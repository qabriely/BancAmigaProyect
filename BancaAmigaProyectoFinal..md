# BancAmigaProyect final

package main;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import models.*;
public class Main {
    public static void main(String args[]) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.YEAR, calendar.MONTH, calendar.DATE, 8, 30);
        Queue<Client> queueClients = Files.queueClients();
        Stack<Transaction> transactions = new Stack();
        int op;
        boolean isExit = false;
        do {
            Display.menuMain();
            op = Input.option(3, "Elija una opción: ");
            switch (op) {
                case 1:
                    boolean finished = false;
                    int request = 0;
                    double amount;
                    do {
                        Client currentClient  = queueClients.dequeue();
                        do {
                            Display.menuTicketOffice(currentClient.getName());
                            op = Input.option(6, "Elija la opcion a realizar: ");
                            switch (op) {
                                case 1:
                                    amount = Input.amount("Ingrese la cantidad a retirar: ");
                                    boolean correct = currentClient.withdrawal(amount);
                                    if (correct) {
                                        Display.waitClient(4);
                                        transactions.push(new Transaction("RETIRO", amount));
                                        calendar.add(calendar.MINUTE, 4);
                                    } else {
                                         Display.error("Fondo insuficiente");
                                    }
                                    break;
                                case 2:
                                    amount = Input.amount("Ingrese la cantidad a depositar: ");
                                    currentClient.Deposit(amount);
                                    Display.waitClient(3);
                                    transactions.push(new Transaction("DEPOSITO", amount));
                                    calendar.add(calendar.MINUTE, 3);
                                    break;
                                case 3:
                                    Display.text("Estamos consultando sus movimientos");
                                    Display.waitClient(1, 5);
                                    transactions.push(new Transaction("CONSULTA DE MOVIMIENTOS"));
                                    calendar.add(calendar.MINUTE, 1);
                                    calendar.add(calendar.SECOND, 30);
                                    break;
                                case 4:
                                    Display.text("Actualizando libretas");
                                    Display.waitClient(5);
                                    transactions.push(new Transaction("ACTUALIZANDO LIBRETAS"));
                                    calendar.add(calendar.MINUTE, 5);
                                    break;
                                case 5:
                                    Display.paymentServices();
                                    op = Input.option(4, "Elija el servicio a pagar: ");
                                    Display.waitClient(2);
                                    transactions.push(new Transaction("PAGO SERVICIOS"));
                                    calendar.add(calendar.MINUTE, 2);
                                    break;
                                case 6:
                                    finished = true;
                                    break;
                                default:
                                    if (op != -1) {
                                        Display.error("La opcion no es valida.");
                                    }
                                }
                            request++;
                        } while ((!finished) && (request != 5));
                    Display.text("Operaciones terminadas");
                    } while ((calendar.HOUR != 1) && (!queueClients.isEmpty()));
                    if (queueClients.isEmpty()) {
                        Display.withoutQueue();
                    }
                    break;
                    case 2:
                        if (queueClients.isEmpty()) {
                            Stack<Transaction> operations = new Stack();
                            while (!transactions.isEmpty()) {
                                operations.push(transactions.pop());
                            }
                            Files.saveOperations(operations);
                        } else {
                            Display.withQueue();
                        }
                        break;
                    case 3: 
                        Display.exitSystem();
                        isExit = true;
                        break;
                    default:
                        if (op != -1) {
                            Display.error("La opcion no es valida.");
                        }
            }
        } while(!isExit);
    }
}
package models;

import java.util.Scanner;

public class Input {
    static Scanner sc = new Scanner(System.in);
    
  
     * @param max El numero maximo de opciones del usuario
     * @param msj El mesaje que se le muestra al usuario
     * @return La opcion del usuario que es un nuemero entre 1 y max, en caso de error retorna -1
     */
    public static int option(int max, String msj) {
        int op = -1;
        try {
            do {
                if (((op < 1) || (op > max)) && (op != -1)) {
                    Display.error("La opcion no es valida");
                }
                System.out.print("\n\t" + msj);
                String opt = sc.next();
                op = Integer.parseInt(opt);
            } while ((op < 1) || (op > max));
        } catch(NumberFormatException nfe) {
           Display.error("La opcion debe ser un numero");
        }
        return op;
    }
    
    public static double amount(String msj) {
        double amount = 0.0;
        boolean successful = false;
        do {
            try {
                System.out.print("\n\t" + msj);
                String amountS = sc.next();
                amount = Double.parseDouble(amountS);
                successful = true;
            } catch(NumberFormatException nfe) {
               Display.error("La opcion debe ser un numero");
            }
        } while (!successful);
        return amount;
    }
}
package models;

public class Node<T> {
    private T value;
    private Node<T> next;

    public Node() {
        this.value = null;
        this.next = null;
    }
      @param v El valor del nodo
      @param next El nodo siguiente
    public Node(T v, Node<T> next){
        this.value = v;
        this.next = next;
    }
     * @param v 
     */
    public Node(T v) {
        this.value = v;
        this.next = null;
    }
    
     * @return El siguiente nodo
     */
    public Node<T> getNext() {
       return this.next;
    }
    
    /**
     * Metodo que modifica el siguiente nodo
     *
     * @param next El nodo por el cual se cambiara el actual
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    /**
     * Metodo que obtiene el valor del nodo
     *
     * @return El siguiente nodo
     */
    public T getValue() {
       return this.value;
    }
    
    /**
     * Metodo que modifica el siguiente nodo
     *
     * @param v El valor por el cual se cambiara el actual
     */
    public void setValue(T v) {
        this.value = v;
    }
}
package models;


public class Queue<T> {
    private Node<T> top, tail;
    private int size;

    /**
     * Constructor por defecto de la cola
     */
    public Queue() {
        top = tail = null;
        size = 0;
    }
    
    /**
     * Metodo que retorna la longitud de la cola
     * 
     * @return La longitud de la cola 
     */
    public int getSize() {
        return size;
    }
         * @return true si la cola esta vacia, false si la cola no esta vacia 
     */
    public Boolean isEmpty() {
        return (top == null);
    }
     /**
     * Metodo que retorna el frente de la cola
     * 
     * @return null si la cola esta vacia, sino el valor contenido en el frente
     */
    public T front() {
        if (isEmpty()) {
            return null;
        } else {
            return top.getValue();
        }
    }
    
    /**
     * Metodo que inserta al final de la cola
     * 
     * @param v El nuevo nodo de la cola 
     */
    public void enqueue(T v) {
        Node<T> newNode = new Node(v, null);
        if (isEmpty()) {
            top = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }
    
     /**
     * Metodo que remueve al principio de la cola
     * 
     * @return El valor contenido en el frente de la cola
     */
    public T dequeue() {
        T aux;
        if (isEmpty()) {
            aux = null;
        } else {
            aux = top.getValue();
            top = top.getNext();
            size--;
            if (isEmpty()){
                tail = null;
            }
        }
        return aux;
    }

}
package models;

public class Stack<T> {

    private Node<T> top;
    private int size;

    /**
     * Constructor por defecto de la pila
     */
    public Stack() {
        this.top = null;
        this.size = 0;
    }
    
    /**
     * Metodo que retorna la longitud de la pila
     * 
     * @return size la longitud de la pila 
     */
    public int getSize() {
        return this.size;
    }
    
    /**
     * Metodo que verifica si la pila esta vacia
     * 
     * @return true si la pila esta vacia, false si la pila no esta vacia 
     */
    public Boolean isEmpty() {
        return (this.top == null);
    }
    
    /**
     * Metodo que inserta en la cima un nodo
     * 
     * @param v El valor del nuevo nodo a la pila 
     */
    public void push(T v) {
        Node<T> newNode = new Node(v, this.top);
        this.top = newNode;
        this.size++;
    }
    
    /**
     * Metodo muestra la cima y la elimina de la pila
     *  
     * @return null si la pila esta vacia, sino el valor contenido en la cima
     */
    public T pop() {
        T aux;
        if (isEmpty()) {
            return null;
        } else {
            aux = this.top.getValue();
            this.top = this.top.getNext();
            this.size--;
            return aux;
        }
    }
    
    /**
     * Metodo muestra la cima sin elimilarla
     *
     * @return null si la pila esta vacia, el valor contenido en la cima
     */
    public Object peek() {
        if (isEmpty()) {
            return null;
        } else {
            return this.top.getValue();
        }
    }
}

package models;
public class Transaction {
    private String name;
    private double amount;

    public Transaction(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public Transaction(String name) {
        this.name = name;
        this.amount = 0.0;
    }
    
    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isWithAmount() {
        return this.amount > 0;
    }
}
package models;


public class Display {
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
                Thread.sleep(1000); // Duerme el hilo durante 1000 milisegundos, 1 segundo pues
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
                Thread.sleep(1000); // Duerme el hilo durante 10000 milisegundos, 10 segundo pues
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
package models;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Files {

    public static Queue<Client> queueClients() {
        Queue<Client> clients = new Queue();
        Client tempClient = new Client();
        File fileClients = new File("src\\models\\Clientes.txt");
        File fileClientsSlopes = new File("src\\models\\Clientes_pendientes.txt");
        Scanner sc;
        String name, surname, ci;
        name = null;
        surname = null;
        ci = null;
        double balance;
        String line;
        if (fileClientsSlopes.exists()) {
            try {
                sc = new Scanner(fileClientsSlopes);
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    String[] separator = line.split("::");
                    name = separator[0];
                    surname = separator[1];
                    ci = separator[2];
                    balance = Double.parseDouble(separator[3]);
                    tempClient = new Client(name, surname, ci, balance);
                    clients.enqueue(tempClient);
                }
            } catch (IOException ioe) {
                Display.error("El archivo \"clientes_pendientes.txt\" no se puede leer.");
            }
            fileClientsSlopes.delete();
        }
        try {
            sc = new Scanner(fileClients);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] separator = line.split("::");
                name = separator[0];
                surname = separator[1];
                ci = separator[2];
                balance = Double.parseDouble(separator[3]);
                tempClient = new Client(name, surname, ci, balance);
                clients.enqueue(tempClient);
            }
        } catch (FileNotFoundException fnfe) {
            Display.error("El archivo \"Clientes.txt\" no encontrado.");
        } catch (IOException ioe) {
            Display.error("El archivo \"Clientes.txt\" no se puede leer.");
        }
        return clients;
    }
    
     /**
     *
     * Metodo que guarda la cola de clientes en un archivo .txt
     *
     * @param slopes Los clientes que quedaron haciendo cola
     */
    public static void clientsSlopes(Queue<Client> slopes) {
        FileWriter file = null;
        BufferedWriter bw = null;
        if (slopes.isEmpty()) {
            return;
        } else {
            try {
                Client tempClient = slopes.dequeue();
                file = new FileWriter("clientes_pendientes.txt", true);
                bw = new BufferedWriter(file);
                bw.write(tempClient.getName() + "::" + tempClient.getSurname() + "::" + tempClient.getCI());
                bw.newLine();
                bw.flush();
            } catch (FileNotFoundException fnfe) {
                Display.error("El archivo \"Clientes_pendientes.txt\" no encontrado.");
            } catch (IOException ioe) {
                Display.error("El archivo \"Clientes_pendientes.txt\" no se puede leer.");
            } finally {
                try {
                    if(bw != null) { 
                        bw.close();
                    }
                    if(file != null) { 
                        file.close();
                    } 
                } catch (IOException ioe) { }
            }
            clientsSlopes(slopes);
            return;
        }
    }

    public static void saveOperations(Stack<Transaction> operations) {
        File oldFile = new File("Taquilla.log");
        if (oldFile.exists()) {
            LocalDate date = LocalDate. now();
            LocalDate previous = date.minusDays(1);
            int day = previous.getDayOfMonth();
            int month = previous.getMonthValue();
            int year = previous.getYear();
            File newfile = new File("taquilla" + day + "-" + month + "-" + year);
            oldFile.renameTo(newfile);
        }
        FileWriter file = null;
        BufferedWriter bw = null;
        try {
            while (!operations.isEmpty()) {
                Transaction tempTransaction = operations.pop();
                file = new FileWriter("Taquilla.log", true);
                bw = new BufferedWriter(file);
                bw.write(tempTransaction.getName() + ".");
                if (tempTransaction.isWithAmount()) {
                    bw.write(" CANTIDAD: " + tempTransaction.getAmount());
                }   
                bw.newLine();
                bw.flush();
            }
        } catch (FileNotFoundException fnfe) {
            Display.error("El archivo \"Taquilla.log\" no encontrado.");
        } catch (IOException ioe) {
            Display.error("El archivo \"Taquilla.log\" no se puede leer.");
        } finally {
            try {
                if(bw != null) { 
                    bw.close();
                }
                if(file != null) { 
                    file.close();
                } 
            } catch (IOException ioe) { }
        }
    }
}

clientes:
Jose::Lopez::12.345.678::123.4
Maria::Marcano::9.101.112::567.8
Luis::Salazar::13.141.516::91.0
Carmen::Teran::17.181.920::111.2
Carlos::Hernandez::21.222.324::131.4
Ana::Garcia::25.262.728::151.6
Juan::Malaver::29.303.132::171.8
Guillermo::Moreno::6.343.536::192.0
Hilda::Reyes::10.383.940::212.2
Doris::Velasquez::5.424.344::232.4
