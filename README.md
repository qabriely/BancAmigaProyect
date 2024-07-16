# BancAmigaProyect

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bancamiga {
    private final List<Cliente> clientes;
    private final Taquilla taquilla;

    public Bancamiga() {
        this.clientes = new ArrayList<>();
        this.taquilla = new Taquilla();
    }

    public void cargarClientes(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                Cliente cliente = new Cliente(datos[0], Double.parseDouble(datos[1]));
                clientes.add(cliente);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }
    }

    public void atenderClientes() {
        clientes.stream().map((cliente) -> {
            System.out.println("Bienvenido " + cliente.getNombre());
            return cliente;
        }).forEachOrdered((cliente) -> {
            taquilla.atenderCliente(cliente);
        });
    }

    public static void main(String[] args) {
        Bancamiga bancamiga = new Bancamiga();
        bancamiga.cargarClientes("clientes.in");
        bancamiga.atenderClientes();
    }
}

class Taquilla {
    public void atenderCliente(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        int contador = 0;

        while (contador < 5) {
            System.out.println("Menu de opciones:");
            System.out.println("1. Deposito");
            System.out.println("2. Retiro");
            System.out.println("3. Consulta de saldo");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese monto a depositar: ");
                    double montoDeposito = scanner.nextDouble();
                    cliente.depositarDinero(montoDeposito);
                    guardarTransaccion(cliente, "Deposito", montoDeposito);
                    break;
                case 2:
                    System.out.print("Ingrese monto a retirar: ");
                    double montoRetiro = scanner.nextDouble();
                    if (cliente.retirarDinero(montoRetiro)) {
                        guardarTransaccion(cliente, "Retiro", montoRetiro);
                    } else {
                        System.out.println("No tiene suficiente saldo");
                    }
                    break;
                case 3:
                    System.out.println("Saldo actual: " + cliente.getSaldo());
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida");
            }

            contador++;
        }
    }

    private void guardarTransaccion(Cliente cliente, String tipo, double monto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transacciones.in", true))) {
            writer.write(cliente.getNombre() + "," + tipo + "," + monto + "\n");
        } catch (IOException e) {
            System.out.println("Error guardando transacción: " + e.getMessage());
        }
    }
}

class Cliente {
    private final String nombre;
    private double saldo;

    public Cliente(String nombre, double saldo) {
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositarDinero(double monto) {
        saldo += monto;
    }

    public boolean retirarDinero(double monto) {
        if (monto <= saldo) {
            saldo -= monto;
            return true;
        } else {
            return false;
        }
    }
}
