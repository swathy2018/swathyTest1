package com.wikia.webdriver.elements.mercury.components.discussions.mobile;

import com.wikia.webdriver.elements.mercury.components.discussions.common.ContributionEditor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class PostEditorMobile extends ContributionEditor {

  @Getter
  @FindBy(className = "new-post")
  private WebElement postsCreator;

  @Getter
  @FindBy(css = ".editor-close")
  private WebElement cancelButton;

  @Getter
  @FindBy (className = "discussion-standalone-editor")
  private WebElement editor;

  @Getter
  @FindBy(css = ".discussion-standalone-editor .discussion-standalone-editor-save-button")
  private WebElement submitButton;

  @Getter
  @FindBy(css = "#categoryPickerButtonMobile")
  private WebElement addCategoryButton;

  @Getter
  @FindBy (css = ".discussion-standalone-editor .discussion-textarea-with-counter")
  private WebElement titleTextarea;

  @Getter
  @FindBy(css = ".discussion-standalone-editor .discussion-standalone-editor-textarea:not([disabled])")
  private WebElement descriptionTextarea;

}
