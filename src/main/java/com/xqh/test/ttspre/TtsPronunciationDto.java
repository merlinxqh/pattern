/**
 * 
 */
package com.xqh.test.ttspre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author andrew
 *
 */
@SuppressWarnings("serial")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TtsPronunciationDto implements Serializable {

	private String audio;
	private String original;
	private String target;
	private Integer type;

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "TtsPronunciationDto [original=" + original + ", target=" + target + ", type=" + type + "]";
	}
}
