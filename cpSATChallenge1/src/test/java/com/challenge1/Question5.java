package com.challenge1;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * 5 Create JUnit Test Suite for all JUnit scripts in this paper
 */
public class Question5 {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(JunitSuite5.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());

	}

}
