package controllers.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to gather detailed information about the final search result the user chose 
 * (after being presented with multiple potential matches to his/her query).
 *
 */
public class FinalResult {
		
	private String name;
	private String photoUrl;
	private String pageId;
	private String desc;
	private String nationality; 
	private String gender; 
	private String dateOfBirth; 
	private String occupation; 
	private String style; 
	private String instanceOf;
	private Map<String, String> identity;
	private Map<String, String> relatedPagesIds;
	private Map<String, String> relatedServices; 
	private List<String> backlinks;

	public FinalResult(String name, String photoUrl, String pageId, String desc, String nationality, String gender, String dateOfBirth, String occupation, String style,String instanceOf) {
		this.name = name;
		this.photoUrl = photoUrl;
		this.pageId = pageId;
		this.desc = desc;
		this.nationality = nationality;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.occupation = occupation;
		this.style = style;
		this.instanceOf=instanceOf;
		this.relatedPagesIds = new HashMap();
		this.relatedServices = new HashMap();
		this.identity = new HashMap();
	}

	@Override
	public String toString() {
		return "FinalResult [name=" + name + ", photoUrl=" + photoUrl + ", pageId=" + pageId + ", desc=" + desc
				+ ", nationality=" + nationality + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth
				+ ", occupation=" + occupation + ", style=" + style + ", instanceOf=" + instanceOf + ", identity="
				+ identity + ", relatedPagesIds=" + relatedPagesIds + ", relatedServices=" + relatedServices + "]";
	}

	public List<String> getBacklinks() {
		return backlinks;
	}
	
	public void setBacklinks(List<String> backlinks) {
		this.backlinks = backlinks;
	}

	public String getInstanceOf() {
		return instanceOf;
	}

	public void setInstanceOf(String instanceOf) {
		this.instanceOf = instanceOf;
	}

	public Map<String, String> getIdentity() {
		if(dateOfBirth != null) {
			identity.put("Date of birth",dateOfBirth);
		}if(nationality != null) {
			identity.put("Nationality",nationality);
		}if(gender != null) {
			identity.put("Gender",gender);
		}if(occupation != null) {
			identity.put("Occupation",occupation);
		}if(style != null) {
			identity.put("Style",style);
		}
		if(instanceOf != null) {
			identity.put("Type",instanceOf);
		}
		return identity;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getPageId() {
		return pageId;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getOccupation() {
		return occupation;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public Map<String, String> getRelatedPagesIds() {
		return relatedPagesIds;
	}

	public Map<String, String> getRelatedServices() {
		return relatedServices;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}
