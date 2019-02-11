package com.home.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handle the arrival display configuration.
 */
@ManagedBean(name = "ArrivalDisplayConfigurationBean")
@ApplicationScoped
public class ArrivalDisplayConfigurationBean implements Serializable {
    /**
     * Needed for proper serializable implementation.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A logger.
     */
    private static final Logger LOG = LogManager.getLogger(ArrivalDisplayConfigurationBean.class.getName());
    /**
     * The path to the default monitor configuration.
     */
    private static final String DEFAULT_ARRIVAL_DISPLAY_CFG = "arrivaldisplay.properties";
    /**
     * The path to put monitor configuration.
     */
    private static final String ARRIVAL_DISPLAY_CFG = "WEB-INF/arrivaldisplay.properties";
    /**
     * Default arrival airport.
     */
    private static final String DEFAULT_ARPO = "DXB";
    /**
     * Default display start date.
     */
    private static final String DEFAULT_START_DATE = "2011-10-02";
    /**
     * Default display start time.
     */
    private static final String DEFAULT_START_TIME = "18:48:00";
    /**
     * Default maximum arrival rows show on the display.
     */
    private static final String DEFAULT_MAX_ENTRIES = "80";
    /**
     * Default base ULI for the RESTful service call.
     */
    private static final String DEFAULT_BASE_URI = "http://localhost:8080/FlightService-war/rest/FlightService/arrivals";
    /**
     * Default data poll interval for display refresh.
     */
    private static final String DEFAULT_POLL_INTERVAL = "30";
    /**
     * The airport to display arrivals for.
     */
    private String arpo = DEFAULT_ARPO;
    /**
     * The display start date.
     */
    private String startDate = DEFAULT_START_DATE;
    /**
     * The display start time.
     */
    private String startTime = DEFAULT_START_TIME;
    /**
     * Maximum arrival rows show on the display.
     */
    private String maxEntries = DEFAULT_MAX_ENTRIES;
    /**
     * The base ULI for the RESTful service call.
     */
    private String baseUri = DEFAULT_BASE_URI;
    /**
     * The data poll interval for display refresh.
     */
    private String pollInterval = DEFAULT_POLL_INTERVAL;

    /**
     * Creates a new instance of ArrivalDisplayConfigurationBean.
     */
    public ArrivalDisplayConfigurationBean() {
        super();
    }

    /**
     * Load the configuration from properties file.
     */
    public void loadConfiguration() {
        Properties props = new Properties();
        FileInputStream fis = null;

        File configDir = new File(System.getProperty("catalina.base"), "conf");
        File cfgPropertiesFile = new File(configDir, DEFAULT_ARRIVAL_DISPLAY_CFG);

        if (!cfgPropertiesFile.exists()) {
            LOG.warn("DEFAULT arrival display configuration not found [" + cfgPropertiesFile.getPath() + "]");
            cfgPropertiesFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(ARRIVAL_DISPLAY_CFG));
        }
        LOG.info(cfgPropertiesFile.getPath());

        try {
            fis = new FileInputStream(cfgPropertiesFile);
            props.load(fis);

            this.arpo = props.getProperty("arpo", DEFAULT_ARPO);
            this.startDate = props.getProperty("startDate", DEFAULT_START_DATE);
            this.startTime = props.getProperty("startTime", DEFAULT_START_TIME);
            this.maxEntries = props.getProperty("maxEntries", DEFAULT_MAX_ENTRIES);
            this.baseUri = props.getProperty("baseUri", DEFAULT_BASE_URI);
            this.pollInterval = props.getProperty("pollInterval", DEFAULT_POLL_INTERVAL);

            fis.close();
        }
        catch (IOException ioex) {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException ex) {
                    LOG.error(ex.getMessage());
                }
            }
            LOG.info(ioex.getMessage());
        }
    }

    /**
     * Get the configured airport.
     *
     * @return the arrival airport
     */
    public String getArpo() {
        return arpo;
    }

    /**
     * Set the airport.
     *
     * @param arpo the arrival airport
     */
    public void setArpo(String arpo) {
        this.arpo = arpo;
    }

    /**
     * Get the display start date.
     *
     * @return the starting date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set the display start date.
     *
     * @param startDate the starting date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the display start time.
     *
     * @return the starting time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Set the display start time.
     *
     * @param startTime the starting time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the maximum number of arrivals to display.
     *
     * @return the maximum number of arrivals to show
     */
    public String getMaxEntries() {
        return maxEntries;
    }

    /**
     * Set the maximum number of arrivals to display.
     *
     * @param maxEntries the maximum number of arrivals
     */
    public void setMaxEntries(String maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * Get the base URI for the RESTful service call.
     *
     * @return the URI
     */
    public String getBaseUri() {
        return baseUri;
    }

    /**
     * Set the base URI for the RESTful service call.
     *
     * @param baseUri the URI
     */
    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    /**
     * Get the display refresh interval.
     *
     * @return the inverval in seconds
     */
    public String getPollInterval() {
        return pollInterval;
    }

    /**
     * Set the display refresh interval.
     *
     * @param pollInterval the interval in seconds
     */
    public void setPollInterval(String pollInterval) {
        this.pollInterval = pollInterval;
    }
}
