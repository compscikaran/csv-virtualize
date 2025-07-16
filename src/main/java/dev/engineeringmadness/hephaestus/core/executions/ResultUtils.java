package dev.engineeringmadness.hephaestus.core.executions;

import dev.engineeringmadness.hephaestus.core.domain.SliceParameters;
import dev.engineeringmadness.hephaestus.interactors.MainController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

public class ResultUtils {

    public static String generateNextPageLink(SliceParameters params, String path) {
        if(params.getPageSize() != null && params.getPageNumber() != null) {
            UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                    .scheme(params.getScheme())
                    .host(params.getHost())
                    .port(params.getPort())
                    .path(path);

            if (StringUtils.isNotBlank(params.getSortColumn()) && params.getSortDirection() != null) {
                url.queryParam("sortColumn", params.getSortColumn());
                url.queryParam("sortDirection", params.getSortDirection());
            }

            url.queryParam("pageSize", params.getPageSize());
            url.queryParam("pageNumber", params.getPageNumber() + 1);
            return url.toUriString();
        }
        return null;
    }
}
