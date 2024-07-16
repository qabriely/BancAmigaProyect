package models;


public class BancAmigaProyect {
    public static void menuMain() {
        System.out.println("\n\tSistema de Taquilla de Bancamiga\n");
        System.out.println("\t1. Iniciar Operaciones");
        System.out.println("\t2. Imprimir Transacciones del Dia");
        System.out.println("\t3. Salir del sistema");
    }
    
    public static void menuTicketOffice(String nameClient) {
        System.out.println("\n\n\t***** Bienvenido " + nameClient + " *****\n");
        System.out.println("\t1. Retiro");
        System.out.println("\t2. Deposito");
        System.out.println("\t3. Consulta de Movimientos");
        System.out.println("\t4. Actualización de Libretas");
        System.out.println("\t5. pago de servicios");
        System.out.println("\t6. Terminar operaciones");
    }
    
    public static void error(String msj) {
        System.out.println("\n\tError: " + msj);
    }
    
    public static void withoutQueue() {
        System.out.println("\n\tNo hay clientes en cola");
        System.out.println("\tPor favor, presione \"3\" para salir del sistema");
    }
    
    public static void withQueue() {
        System.out.println("\n\tHay clientes en cola");
        System.out.println("\tPor favor, atiende a los clientes antes de imprimir las transacciones");
    }
    
    public static void exitSystem() {
        System.out.print("\n\tHa salido del sistema");
        for (int i = 1; i <= 3; i++) {
            System.out.print(".");
            try {
                Thread.sleep(1000); // Duerme el hilo durante 1000 milisegundos, 1 segundo 
            } catch(InterruptedException ie) {
                // ignorar
            }
        }
    }

    public static void text(String text) {
        System.out.print("\n\t" + text);
    }

    public static void waitClient(int wait) {
        System.out.print("\n\tEspere mientras se realiza su operacion: ");
        for (int i = 1; i <= wait; i++) {
            try {
                Thread.sleep(1000); // Duerme el hilo durante 10000 milisegundos, 10 segundos
            } catch(InterruptedException ie) {}
            System.out.print(i + " minutos ");
        }
    }
    
    public static void waitClient(int wait, int sec) {
        waitClient(wait);
        try {
            Thread.sleep(500); 
        } catch(InterruptedException ie) {}
        System.out.print(wait + "." + sec + " minutos");  
    }

    public static void paymentServices() {
        System.out.println("\n\t1. Luz");
        System.out.println("\t2. Agua");
        System.out.println("\t3. Telefonia Movil");
        System.out.println("\t4. Internet"); 
    }
    
}