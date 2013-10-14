package com.wikia.webdriver.Common.Templates;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class NewTestTemplateBeforeClass extends NewTestTemplateCore {

	public NewTestTemplateBeforeClass() {
		super();
	}

	@BeforeClass
	public void start() {
		prepareURLs();
		startBrowser();
	}

	@AfterClass
	public void stop() {
		stopBrowser();
	}
}
