package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.converter.ConverterUtils;
import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentEntryWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentConfigurationRest;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentEntryRest;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentConfigurationUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentLogoUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentUseCase;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebAdapter
@RestController
@RequestMapping("/webcomponent")
public class WebcomponentController {
    private final GetWebcomponentUseCase getWebcomponentUseCase;
    private final ListWebcomponentUseCase listWebcomponentUseCase;
    private final GetWebcomponentConfigurationUseCase getWebcomponentConfigurationUseCase;
    private final GetWebcomponentLogoUseCase getWebcomponentLogoUseCase;
    private final WebcomponentWebConverter webcomponentWebConverter;
    private final WebcomponentEntryWebConverter webcomponentEntryWebConverter;

	public WebcomponentController(GetWebcomponentUseCase getWebcomponentUseCase, ListWebcomponentUseCase listWebcomponentUseCase, GetWebcomponentConfigurationUseCase getWebcomponentConfigurationUseCase, GetWebcomponentLogoUseCase getWebcomponentLogoUseCase, WebcomponentWebConverter webcomponentWebConverter, WebcomponentEntryWebConverter webcomponentEntryWebConverter) {
		this.getWebcomponentUseCase = getWebcomponentUseCase;
		this.listWebcomponentUseCase = listWebcomponentUseCase;
		this.getWebcomponentConfigurationUseCase = getWebcomponentConfigurationUseCase;
		this.getWebcomponentLogoUseCase = getWebcomponentLogoUseCase;
		this.webcomponentWebConverter = webcomponentWebConverter;
		this.webcomponentEntryWebConverter = webcomponentEntryWebConverter;
	}

	@GetMapping
    public Page<WebcomponentEntryRest> listPagedAndFiltered(
                                                           @RequestParam(name = "tags", required = false) String[] tags,
                                                           @RequestParam(name = "searchTerm", required = false, defaultValue = "") String searchTerm,
                                                           @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer requestPageNumber,
                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer requestPageSize,
                                                           @RequestParam(name = "latest", required = false, defaultValue = "false") Boolean latest
                                                           ) {

        Pageable pageRequest;
        if(latest) {
            pageRequest = PageRequest.of(requestPageNumber, requestPageSize, Sort.by("latest"));
        }
        else {
            pageRequest = PageRequest.of(requestPageNumber, requestPageSize);
        }

        List<String> tagList = Collections.emptyList();
        if(tags != null) {
            tagList = Arrays.asList(tags);
        }

        val filter = new ListWebcomponentUseCase.WebcomponentFilter();
        filter.setSearchTerm(searchTerm);
        filter.setTags(tagList);

        val resultPage = listWebcomponentUseCase.listPagedAndFiltered(pageRequest, filter);

        return webcomponentEntryWebConverter.convert(resultPage);
    }

    @GetMapping("/{uuid}")
    public WebcomponentRest getOne(@PathVariable String uuid) {
        return webcomponentWebConverter.convert(getWebcomponentUseCase.getWebcomponentById(uuid));
    }

    @GetMapping("/{uuid}/config")
    public WebcomponentConfigurationRest getConfiguration(@PathVariable String uuid) {
        val configuration = getWebcomponentConfigurationUseCase.getConfiguration(uuid);

		return toRest(configuration);
    }

    @GetMapping("/{uuid}/config/{versionTag}")
    public WebcomponentConfigurationRest getConfigurationForVersion(@PathVariable String uuid, @PathVariable String versionTag) {
		val configuration = getWebcomponentConfigurationUseCase.getConfiguration(uuid, versionTag);

		return toRest(configuration);
    }

    @GetMapping(value = "/{uuid}/logo", produces = "image/png") //TODO: actually, the mimetype is never checked. might also be gif or webp
    public byte[] getLogoImage(@PathVariable String uuid) {
        return getWebcomponentLogoUseCase.getLogoImage(uuid);
	}

	private WebcomponentConfigurationRest toRest(WebcomponentConfiguration domain) {
		val rest = new WebcomponentConfigurationRest();

		ConverterUtils.copyProperties(domain, rest);
		rest.setScriptSources(domain.getScriptSources()); //TODO: check if that is even needed

		return rest;
	}
}
