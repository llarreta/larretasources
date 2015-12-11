package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import ar.com.larreta.commons.controllers.impl.LoginControllerImpl;

public class SmartTraceLoginController extends LoginControllerImpl {

	private List<String> images;

	public SmartTraceLoginController() {
		super();
	}

	@PostConstruct
	public void init() {
		//FIXME: Externalizar!!! no hardcodear en el codigo nombre de archivos
		String image = "fondoLogin.jpg";
		String image1 = "Imagen 1.jpg";
		String image2 = "Imagen 2.jpg";
		String image3 = "Imagen 3.jpg";

		this.images = new ArrayList<String>();
		images.add(image);
		images.add(image1);
		images.add(image2);
		images.add(image3);
	}

	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}

	/**
	 * @param images
	 *            the images to set
	 */
	public void setImages(List<String> images) {
		this.images = images;
	}

}
