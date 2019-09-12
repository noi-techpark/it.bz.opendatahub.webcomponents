package it.bz.opendatahub.webcomponents.common.data.model;

import it.bz.opendatahub.webcomponents.common.data.Model;
import it.bz.opendatahub.webcomponents.common.hibernate.usertype.impl.ListStringUserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@TypeDef(name = ListStringUserType.NAME, typeClass = ListStringUserType.class)

@Getter
@Setter
@Entity
@Table(name = "spdx")
public class SpdxLicenseModel implements Model {
    private String reference;

    private Boolean isDeprecatedLicenseId;

    private String detailsUrl;

    private String referenceNumber;

    private String name;

    @Id
    private String licenseId;

    @Type(type = ListStringUserType.NAME)
    private List<String> seeAlso;

    private Boolean isOsiApproved;
}
