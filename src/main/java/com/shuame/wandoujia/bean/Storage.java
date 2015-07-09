package com.shuame.wandoujia.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Storage")
@XmlType(name = "Storage", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class Storage implements Cloneable {

	/**  */
	@XmlElement(name = "Video")
	private String _video;

	/**  */
	@XmlElement(name = "Music")
	private String _music;

	/**  */
	@XmlElement(name = "Book")
	private String _book;

	/**  */
	@XmlElement(name = "Picture")
	private String _picture;

	public String getVideo() {
		return _video;
	}

	public void setVideo(String video) {
		_video = video;
	}

	public String getMusic() {
		return _music;
	}

	public void setMusic(String music) {
		_music = music;
	}

	public String getBook() {
		return _book;
	}

	public void setBook(String book) {
		_book = book;
	}

	public String getPicture() {
		return _picture;
	}

	public void setPicture(String picture) {
		_picture = picture;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	protected Storage clone() throws CloneNotSupportedException {
		return (Storage) super.clone();
	}

}
