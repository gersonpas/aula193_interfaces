package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

    private Double pricePerDay;
    private Double pricePerHour;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental){
        long t1 = carRental.getStart().getTime();  // pega o tempo em milisegundos
        long t2 = carRental.getFinish().getTime();
        double hours = (double) (t2 - t1) / 1000 / 60 /60;  // transforma os milisegundos em horas e minutos.

        double basicPayment;
        if (hours <= 12.0){
           basicPayment = Math.ceil(hours) * pricePerHour;  // arredonda o tempo em horas pra cima se necessario.
        }
        else {
           basicPayment = Math.ceil(hours / 24) * pricePerDay;  // arredonda o tempo em dias pra cima se necessario.
        }

        double tax = taxService.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));


    }
}

