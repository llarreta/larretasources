package ar.com.larreta.rest.messages;

import java.io.Serializable;

import org.apache.log4j.Logger;

import ar.com.larreta.annotations.Log;

public abstract class Body extends JSONable implements Serializable {
	private static @Log Logger LOG;
}
