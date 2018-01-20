package com.example.features;


import com.example.beans.Email;
import com.example.beans.Postbox;
import com.example.beans.PostboxImpl;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class DemoStepdefs {

    Postbox postbox = new PostboxImpl();

    String rec;

    @Given("^收件人'(?<recip>[^']+)'$")
    public void given(String recip) {
        this.rec = recip;
    }

    @When("^给收件人发送邮件'(?<title>[^']+)','(?<content>[^']+)'$")
    public void when(String title, String content) throws Throwable {
        Email email = new Email();
        email.setContent(content);
        email.setTitle(title);
        email.setRecipients(rec);
        postbox.putIn(email);
    }

    @Then("^收件人收到邮件,内容为'(?<content>[^']+)'$")
    public void then(String content) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assert postbox.hasEmail(rec);
        assert postbox.size() == 1;
    }
}
