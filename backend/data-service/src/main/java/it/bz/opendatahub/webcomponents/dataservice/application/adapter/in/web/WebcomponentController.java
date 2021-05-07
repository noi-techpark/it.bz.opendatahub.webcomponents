package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.converter.ConverterUtils;
import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.common.util.ThumbnailUtility;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentEntryWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentConfigurationRest;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentEntryRest;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.*;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.http.HttpEntity;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;
import sun.net.www.http.HttpClient;

import javax.net.ssl.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final CreateCodingSandboxUseCase createCodingSandboxUseCase;

	private final Map<String, byte[]> thumbCache = new HashMap<>();
	private final Map<String, Long> thumbCacheTimer = new HashMap<>();

	public WebcomponentController(GetWebcomponentUseCase getWebcomponentUseCase, ListWebcomponentUseCase listWebcomponentUseCase, GetWebcomponentConfigurationUseCase getWebcomponentConfigurationUseCase, GetWebcomponentLogoUseCase getWebcomponentLogoUseCase, WebcomponentWebConverter webcomponentWebConverter, WebcomponentEntryWebConverter webcomponentEntryWebConverter, CreateCodingSandboxUseCase createCodingSandboxUseCase) {
		this.getWebcomponentUseCase = getWebcomponentUseCase;
		this.listWebcomponentUseCase = listWebcomponentUseCase;
		this.getWebcomponentConfigurationUseCase = getWebcomponentConfigurationUseCase;
		this.getWebcomponentLogoUseCase = getWebcomponentLogoUseCase;
		this.webcomponentWebConverter = webcomponentWebConverter;
		this.webcomponentEntryWebConverter = webcomponentEntryWebConverter;
		this.createCodingSandboxUseCase = createCodingSandboxUseCase;
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

	@GetMapping(value = "/{uuid}/logo/thumb", produces = "image/jpg")
	public byte[] getLogoImageThumb(@PathVariable String uuid) throws IOException {
		if(thumbCacheTimer.containsKey(uuid) && thumbCacheTimer.get(uuid) < System.currentTimeMillis()) {
			thumbCacheTimer.remove(uuid);
			thumbCache.remove(uuid);
		}

		if(thumbCache.containsKey(uuid)) {
			return thumbCache.get(uuid);
		}

		val fullLogoData = getLogoImage(uuid);

		val image = ThumbnailUtility.createThumbnailForImage(new ByteArrayInputStream(fullLogoData), 400, 250);

		val data = ThumbnailUtility.toJpg(image).toByteArray();

		val max = 100;
		val min = 10;
		val range = max - min + 1;
		val rand = (int)(Math.random() * range) + min;

		thumbCacheTimer.put(uuid, System.currentTimeMillis() + 60 * 60 * 1000L + rand * 1000L);
		thumbCache.put(uuid, data);

		return data;
	}

	private WebcomponentConfigurationRest toRest(WebcomponentConfiguration domain) {
		val rest = new WebcomponentConfigurationRest();

		ConverterUtils.copyProperties(domain, rest);
		rest.setScriptSources(domain.getScriptSources()); //TODO: check if that is even needed

		return rest;
	}

	@PostMapping(value = "/createCodeSandbox")
	public String createCodeSandbox(@RequestBody @Valid CreateCodingSandboxUseCase.CodeSandboxRequest request) throws JSONException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException {
		return createCodingSandboxUseCase.createCodeSandbox(request);
	}
}
