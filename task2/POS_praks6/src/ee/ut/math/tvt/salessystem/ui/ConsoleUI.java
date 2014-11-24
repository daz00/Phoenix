package ee.ut.math.tvt.salessystem.ui;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Client;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;

/**
 * A simple CLI (limited functionality).
 *
 */
public class ConsoleUI {
    private static final Logger log = Logger.getLogger(ConsoleUI.class);

    private final SalesDomainController dc;

    private List<StockItem> cart;

    private List<StockItem> warehouse;

    private Client selectedClient;

    public ConsoleUI(SalesDomainController domainController) {
        this.dc = domainController;

        cart = new ArrayList<StockItem>();
        warehouse = new ArrayList<StockItem>();
    }

    /**
     * Run the sales system CLI.
     */
    public void run() {
        try {
            // populate warehouse with goodies
            populateWarehouse();

            System.out.println("===========================");
            System.out.println("=       Sales System      =");
            System.out.println("===========================");
            printUsage();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            String command = "";

            while (true) {
                System.out.print("> ");
                command = in.readLine();
                processCommand(command.trim().toLowerCase());
                System.out.println("Done. ");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void populateWarehouse() {
        warehouse = dc.getAllStockItems();
    }

    private void showStock(List<StockItem> stock) {
        System.out.println("-------------------------");
        for (StockItem si : stock) {
            System.out.println(si.getId() + " "
                    + si.getName() + " "
                    + si.getPrice() + "Euro ("
                    + si.getQuantity() + " items)");
        }
        if (stock.size() == 0) {
            System.out.println("\tNothing");
        }
        System.out.println("-------------------------");
    }

    private void printUsage() {
        System.out.println("-------------------------");
        System.out.println("Usage:");
        System.out.println("h\t\tShow this help");
        System.out.println("s\t\tSelect client");
        System.out.println("w\t\tShow warehouse contents");
        System.out.println("c\t\tShow cart contents");
        System.out
                .println("a IDX NR \tAdd NR of stock item with index IDX to the cart");
        System.out.println("p\t\tPurchase the shopping cart");
        System.out.println("r\t\tReset the shopping cart");
        System.out.println("q\t\tExit");
        System.out.println("-------------------------");
    }

    private StockItem getStockItemById(int id) {
        for (StockItem item : warehouse) {
            if (item.getId() == id)
                return item;
        }
        throw new NoSuchElementException();
    }

    private void processCommand(String command) {
        String[] c = command.split(" ");

        if (c[0].equals("h")) {
            printUsage();

        } else if (c[0].equals("q")) {
            dc.endSession();
            System.exit(0);

        } else if (c[0].equals("w")) {
            showStock(warehouse);

        } else if (c[0].equals("c")) {
            showStock(cart);

        } else if (c[0].equals("s")) {
            selectClient();

        } else if (c[0].equals("p")) {
            if(selectedClient == null) {
                System.out.println("You must select a client first!");
                return;
            }
            try {
                List<SoldItem> soldItems = new ArrayList<SoldItem>();
                for(StockItem stockItem : cart) {
                    soldItems.add(new SoldItem(stockItem, stockItem.getQuantity()));
                }
                dc.submitCurrentPurchase(soldItems, selectedClient);
                cart.clear();
            } catch (VerificationFailedException e) {
                log.error(e.getMessage());
            }

        } else if (c[0].equals("r")) {

            dc.cancelCurrentPurchase();
            cart.clear();

        } else if (c[0].equals("a") && c.length == 3) {

            int idx = Integer.parseInt(c[1]);
            int amount = Integer.parseInt(c[2]);
            StockItem item = getStockItemById(idx);
            item.setQuantity(Math.min(amount, item.getQuantity()));
            cart.add(item);
        }
    }

    private void selectClient() {
        List<Client> clients = dc.getAllClients();
        System.out.println("Select client:");
        int index = 0;
        for(Client client : clients) {
            System.out.println(++index + ") " + client.getFirstName());
        }
        System.out.print("> ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String cmd;
        try {
            cmd = in.readLine().trim();
            index = Integer.valueOf(cmd);
        }
        catch (NumberFormatException e) {
            System.out.println("Illegal client number!");
            return;
        }
        catch (IOException e) {
            log.error(e);
            return;
        }
        if(index < 1 || index > clients.size()) {
           System.out.println("Illegal client number!");
           return;
        }
        //Test getCLient
        selectedClient = dc.getClient(clients.get(index - 1).getId());
        System.out.println("Selected client: " + selectedClient.getFirstName());
    }

}
