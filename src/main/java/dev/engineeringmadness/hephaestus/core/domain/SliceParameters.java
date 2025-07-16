package dev.engineeringmadness.hephaestus.core.domain;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SliceParameters {
    private Integer pageSize;
    private Integer pageNumber;
    private String sortColumn;
    private SortDirection sortDirection;
    private Integer top;
    private String host;
    private Integer port;
    private String scheme;

    public SliceParameters(HttpServletRequest request) {
        this.pageSize = request.getParameter("pageSize") != null ? Integer.parseInt(request.getParameter("pageSize")) : null;
        this.pageNumber = request.getParameter("pageNumber") != null ? Integer.parseInt(request.getParameter("pageNumber")) : null;
        this.sortColumn = request.getParameter("sortColumn");
        this.sortDirection = request.getParameter("sortDirection") != null ? SortDirection.valueOf(request.getParameter("sortDirection")) : null;
        this.top = request.getParameter("top") != null ? Integer.parseInt(request.getParameter("top")) : null;
        this.host = request.getServerName();
        this.port = request.getServerPort();
        this.scheme = request.getScheme();
    }
}
