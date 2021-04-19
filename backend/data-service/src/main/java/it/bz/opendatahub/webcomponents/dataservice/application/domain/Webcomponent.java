package it.bz.opendatahub.webcomponents.dataservice.application.domain;

import it.bz.opendatahub.webcomponents.common.data.struct.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Webcomponent {
	private String uuid;

	private String title;

	private String description;

	private String descriptionAbstract;

	private String license;

	private String repositoryUrl;

	private List<Author> copyrightHolders;

	private String image;

	private List<Author> authors;

	private List<String> searchTags;

	private Boolean deleted;
}
