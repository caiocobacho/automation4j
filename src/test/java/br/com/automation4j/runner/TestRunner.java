package br.com.automation4j.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import io.cucumber.junit.Cucumber;

/**
 * @author Caio Cobacho
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/features", plugin = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json", "pretty"}, glue = {"br.com.zup.steps"}, tags = {
        "@SmokeTest"})

public class TestRunner {
	
	



}
