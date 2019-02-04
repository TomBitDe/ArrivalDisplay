package com.home.arrivaldisplay;

import com.home.flightservice.boundary.ArrivalVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
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
    private final String maxEntries = "80";

    private final String baseUri = "http://localhost:8080/FlightService-war/rest/FlightService/arrivals";
    private final String callUri = baseUri + '/' + arpo + '/' + startDate + ' ' + startTime + '/' + maxEntries;

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

    /**
     * Do poll the data
     */
    public void pollData() {
        LOG.debug("Polling data...");
        loadData();
    }

    /**
     * JAX-RS client to load the data
     */
    private void loadData() {
        LOG.debug("RESTful call to [" + callUri + "]...");
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

    /**
     * Create the datatable columns. Dynamic means the columns are NOT defined in the JSF page. It is done here by
     * program.
     */
    private void createDynamicColumns() {
        columns = new ArrayList<>();

        columns.add(new ColumnModel("Flight", "flgtNo", "80"));
        columns.add(new ColumnModel("Scheduled", "schedFlgtDt", "100"));
        columns.add(new ColumnModel("Expected", "expected", "100"));
        columns.add(new ColumnModel("Comments", "comments", ""));
        columns.add(new ColumnModel("Exit", "paxExit", "50"));
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

    /**
     * The datatable column definition. The column header text and the needed column property to show the column values
     * is defined here.
     */
    static public class ColumnModel implements Serializable {
        private final String header;
        private final String property;
        private final String width;

        /**
         * Construct a new ColumnModel
         *
         * @param header   the column header text to display at the top of the column
         * @param property the property that shows the content of the column
         * @param width    the column width
         */
        public ColumnModel(String header, String property, String width) {
            this.header = header;
            this.property = property;
            this.width = width;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }

        public String getWidth() {
            return width;
        }
    }
}
