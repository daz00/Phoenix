package ee.ut.math.tvt.salessystem;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import org.apache.log4j.Logger;

/**
 * Sales system. The main method does the necessary initialization and runs the
 * system.
 */
public class SalesSystem {
    
    private static final Logger log = Logger.getLogger(SalesSystem.class);

    /**
     * Main method.
     */
    public static void main(String args[]) {

        final SalesDomainController domainController = new SalesDomainControllerImpl();

        if (args.length == 1 && args[0].equals("console")) {
            // a small console UI
            ConsoleUI cui = new ConsoleUI(domainController);
            cui.run();
        } else {
            // Swing UI
            log.debug("Strating SalesSystem with the Swing UI ..");
            final SalesSystemUI ui = new SalesSystemUI(domainController);

            ui.setVisible(true);
        }

        log.info("SalesSystem started");

    }

}
