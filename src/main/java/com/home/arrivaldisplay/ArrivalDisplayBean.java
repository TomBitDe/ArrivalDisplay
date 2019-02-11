package com.home.arrivaldisplay;

import com.home.config.ArrivalDisplayConfigurationBean;
import com.home.flightservice.boundary.ArrivalVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(ArrivalDisplayBean.class.getName());
    /**
     * A JaxRs client.
     */
    private final Client jaxRsClient;

    private final ArrivalDisplayConfigurationBean config;

    private String columnName;
    private List<ColumnModel> columns;
    private List<ArrivalVO> arrivals;

    public ArrivalDisplayBean() {
        jaxRsClient = ClientBuilder.newClient();
        config = new ArrivalDisplayConfigurationBean();
    }

    /**
     * Get the arrivals to display.
     *
     * @return the arrivals as a List
     */
    public List<ArrivalVO> getArrivals() {
        return this.arrivals;
    }

    public String getArpo() {
        return config.getArpo();
    }

    public String getStartDate() {
        return config.getStartDate();
    }

    public String getStartTime() {
        return config.getStartTime();
    }

    public String getMaxEntries() {
        return config.getMaxEntries();
    }

    public String getBaseUri() {
        return config.getBaseUri();
    }

    public String getPollInterval() {
        return config.getPollInterval();
    }

    /**
     * Initialize with data and create the datatable column model after bean construction.
     */
    @PostConstruct
    public void init() {
        LOG.debug("Load configuration...");
        config.loadConfiguration();
        LOG.debug("Load data...");
        loadData();

        LOG.debug("Create arrivals model...");
        createArrivalsModel();
    }

    /**
     * Do poll the data.
     */
    public void pollData() {
        LOG.debug("Polling data...");
        loadData();
    }

    /**
     * JAX-RS client to load the data.
     */
    private void loadData() {
        String callUri = getBaseUri() + '/' + getArpo() + '/' + getStartDate() + ' ' + getStartTime() + '/' + getMaxEntries();
        LOG.debug("RESTful call to [" + callUri + "]...");
        arrivals = jaxRsClient.target(callUri)
                .request("application/xml").get(new GenericType<List<ArrivalVO>>() {
        });
    }

    private void createArrivalsModel() {
        createDynamicColumns();
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
         * Construct a new ColumnModel.
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
