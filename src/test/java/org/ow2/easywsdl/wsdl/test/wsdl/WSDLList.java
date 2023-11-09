/**
 * Copyright (c) 2008-2012 EBM WebSourcing, 2012-2018 Linagora
 * 
 * This program/library is free software: you can redistribute it and/or modify
 * it under the terms of the New BSD License (3-clause license).
 *
 * This program/library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the New BSD License (3-clause license)
 * for more details.
 *
 * You should have received a copy of the New BSD License (3-clause license)
 * along with this program/library; If not, see http://directory.fsf.org/wiki/License:BSD_3Clause/
 * for the New BSD License (3-clause license).
 */
 
package org.ow2.easywsdl.wsdl.test.wsdl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas Salatge - EBM WebSourcing
 */
public class WSDLList {

    private final static List<URL> wsdls11 = new ArrayList<URL>();

    private final static List<URL> wsdls20 = new ArrayList<URL>();
    
    static {
        /* WSDL 1.1 */
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/WeatherForecast.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/tuxDroid-CXF.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/importwsdl/CustomerSearch.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/GoogleSearch.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/AmazonWebServices.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/ValidateEmail.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/usweather.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/luhnchecker.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/stockquote.wsdl"));

        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/muller.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/ZipCode.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/CurrencyExchange.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/delayedstockquote.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/country.wsdl"));

        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/sendsmsworld.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/CurrencyConverter.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/soap.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/Artist.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/textgraphic.wsdl"));

        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/webclock.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/PasswordGeneratorService.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/WorldTimeWebService.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/server.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/addresslookup.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/companyInfo.wsdl"));

        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/medicareSupplier.wsdl"));
        /*
         * Disable it because it seems incompatible with the wsdl11to20 transformation...
         * WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
         * "descriptors/otherswsdl/xstatistics.wsdl"));
         */
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/NED.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/CaptchaService.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/exchangerates.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/CalculateAccurateMass.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherElmts/wsdl11/wsdl66.wsdl"));

        //Usecase armee air
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/armee-wsdlsiga.wsdl"));
        WSDLList.wsdls11.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/otherswsdl/armee-wsdl.wsdl"));

        /* WSDL 2.0 */
        WSDLList.wsdls20.add(Thread.currentThread().getContextClassLoader().getResource(
                "descriptors/reservationList.wsdl"));

    }

    /**
     * @return the wsdls11
     */
    public static List<URL> getWsdls11() {
        return WSDLList.wsdls11;
    }

    /**
     * @return the wsdls20
     */
    public static List<URL> getWsdls20() {
        return WSDLList.wsdls20;
    }

}
