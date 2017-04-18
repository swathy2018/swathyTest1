package com.wikia.webdriver.testcases.articlecrudtests;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.TestContext;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.RelatedIssue;
import com.wikia.webdriver.common.core.api.ArticleContent;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.driverprovider.UseUnstablePageLoadStrategy;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.elements.oasis.components.notifications.Notification;
import com.wikia.webdriver.pageobjectsfactory.pageobject.actions.DeletePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.actions.RenamePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialRestorePageObject;

import org.testng.annotations.Test;

import java.util.List;

import static com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject.CONFIRM_NOTIFICATION;

@Test(groups = {"ArticleActionsAdmin"})
public class ArticleActionsAdminTests extends NewTestTemplate {

  @Test(groups = {"ArticleActionsAdmin_001"})
  @UseUnstablePageLoadStrategy
  @RelatedIssue(issueID = "MAIN-9808", comment = "problems with banner notifications")
  @Execute(asUser = User.STAFF)
  public void deleteUndeleteArticle() {
    String articleTitle = "DeleteUndeleArticle";
    new ArticleContent().push(PageContent.ARTICLE_TEXT, articleTitle);

    ArticlePageObject article = new ArticlePageObject().open(articleTitle);
    DeletePageObject deletePage = article.deleteUsingDropdown();
    deletePage.submitDeletion();
    List<Notification> confirmNotifications = article.getNotifications(CONFIRM_NOTIFICATION);
    Assertion.assertEquals(confirmNotifications.size(),1,
            "Number of action confirming notifications is invalid");
    SpecialRestorePageObject restore = article.getNotifications(CONFIRM_NOTIFICATION)
            .stream().findFirst().get().clickUndeleteLinkInBannerNotification();

    restore.verifyRestoredArticleName(articleTitle);
    restore.giveReason(article.getTimeStamp());
    restore.restorePage();
    Assertion.assertTrue(article.getNotifications(CONFIRM_NOTIFICATION).stream().findFirst().isPresent());

    article.verifyArticleTitle(articleTitle);
  }

  @Test(groups = {"ArticleActionsAdmin_002"})
  @UseUnstablePageLoadStrategy
  @Execute(asUser = User.STAFF)
  public void moveArticle() {
    new ArticleContent().push(PageContent.ARTICLE_TEXT);

    ArticlePageObject article = new ArticlePageObject().open();
    String articleOldTitle = article.getArticleTitle();
    String articleNewName = TestContext.getCurrentMethodName() + article.getTimeStamp();
    RenamePageObject renamePage = article.renameUsingDropdown();
    renamePage.rename(articleNewName, false);

    List<Notification> confirmNotifications = article.getNotifications(CONFIRM_NOTIFICATION);

    Assertion.assertEquals(confirmNotifications.size(),1,
            "Number of action confirming notifications is invalid");
    Assertion.assertEquals(confirmNotifications.stream().findFirst().get().getMessage(),
            "\"" + articleOldTitle + "\" has been renamed \"" + articleNewName + "\"",
            "Banner notification messsage is invalid");
    Assertion.assertEquals(article.getArticleName(), articleNewName,
            "New article title is invalid");
  }
}
