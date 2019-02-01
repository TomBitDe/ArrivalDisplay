package com.home.arrivaldisplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Arrival data provider.
 */
@ManagedBean(name = "arrivalDisplay")
@RequestScoped
public class ArrivalDisplayBean implements java.io.Serializable {
    private static final Logger LOG = LogManager.getLogger(ArrivalDisplayBean.class.getName());
    private final Client jaxRsClient;

    private String arpo = "DXB";
    private String startDate = "2011-10-02";
    private String startTime = "18:48:00";
    private String endDateTime = "2011-10-02 19:48:00";
    private String maxEntries = "80";

    private String baseUri = "http://localhost:8080/FlightService-war/rest/FlightService/arrivals";
    private String callUri = baseUri + '/' + arpo + '/' + startDate + ' ' + startTime + '/' + maxEntries;

    private String columnName;
    private List<ColumnModel> columns;
    private List<ArrivalVO> arrivals;

    public ArrivalDisplayBean() {
        jaxRsClient = ClientBuilder.newClient();
        init();
    }

    public String getArpo() {
        return arpo;
    }

    public void setArpo(String arpo) {
        this.arpo = arpo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void init() {
        LOG.debug("Load data...");
        loadData();

        createArrivalsModel();
    }

    public void pollData() {
        LOG.debug("Polling data...");
        loadData();
    }

    /**
     * JAX-RS client to poll the data
     */
    private void loadData() {
        LOG.debug("RESTful call to [" + callUri + ']');
        arrivals = jaxRsClient.target(callUri)
                .request("application/xml").get(new GenericType<List<ArrivalVO>>() {
        });
    }

    public void createArrivalsModel() {
        createDynamicColumns();
    }

    public List<ArrivalVO> getArrivals() {
        return this.arrivals;
    }

    private void createDynamicColumns() {
        columns = new ArrayList<>();

        columns.add(new ColumnModel("Flight", "flgtNo"));
        columns.add(new ColumnModel("Scheduled", "schedFlgtDt"));
        columns.add(new ColumnModel("Expected", "expected"));
        columns.add(new ColumnModel("Comments", "comments"));
        columns.add(new ColumnModel("Exit", "paxExit"));
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    static public class ColumnModel implements Serializable {
        private final String header;
        private final String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }
}
