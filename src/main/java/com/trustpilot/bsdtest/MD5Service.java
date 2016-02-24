package com.trustpilot.bsdtest;

import java.security.NoSuchAlgorithmException;

@FunctionalInterface
public interface MD5Service {
	String md5Convert(String str) throws NoSuchAlgorithmException;
}
