/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.blogic.utilities;


public class FilmNationalityList {
   public static String[] listName;


   public FilmNationalityList(String country) {
        if (country.equals("IT")) {
            listName = new String[]{

                    "ALBANIA,AL",
                    "ALGERIA",
                    "ANDORRA",
                    "ANGOLA",
                    "ANGUILLA",
                    "ANTARTIDE FRANCESE",
                    "ANTIGUA/BARBUDA",
                    "ANTILLE OLANDESI",
                    "ARABIA SAUDITA",
                    "ARCIPELAGO DI COOK",
                    "ARGENTINA",
                    "ARMENIA",
                    "ARUBA",
                    "ASCENSION",
                    "AUSTRALIA",
                    "AUSTRIA",
                    "AZERBAIGIAN",
                    "BAHAMA",
                    "BAHREIN",
                    "BANGLADESH",
                    "BARBADOS",
                    "BELGIO",
                    "BELIZE",
                    "BENIN",
                    "BERMUDE",
                    "BHUTAN",
                    "BIELORUSSIA",
                    "BOLIVIA",
                    "BOSNIA-ERZEGOVINA",
                    "BOTSWANA",
                    "BRASILE",
                    "BRUNEI",
                    "BULGARIA",
                    "BURKINA FASO",
                    "BURUNDI",
                    "CAMBOGIA",
                    "CAMERUN",
                    "CANADA",
                    "CAYMAN",
                    "CENTRAFRICA",
                    "CIAD",
                    "CILE",
                    "CINA",
                    "CIPRO",
                    "CIPRO",
                    "CITTA' DEL VATICANO",
                    "COLOMBIA",
                    "COMORE",
                    "COREA DEL NORD",
                    "COREA DEL SUD",
                    "COSTA D'AVORIO",
                    "COSTA RICA",
                    "CROAZIA",
                    "CUBA",
                    "DANIMARCA",
                    "DOMINICA",
                    "ECUADOR",
                    "EGITTO",
                    "EMIRATI ARABI UNITI",
                    "ERITREA",
                    "ESTONIA",
                    "ETIOPIA",
                    "FILIPPINE",
                    "FINLANDIA",
                    "FRANCIA",
                    "GABON",
                    "GAMBIA",
                    "GEORGIA",
                    "GEORGIA DEL SUD E S",
                    "GERMANIA",
                    "GHANA",
                    "GIAMAICA",
                    "GIAPPONE",
                    "GIBILTERRA",
                    "GIBUTI",
                    "GIORDANIA",
                    "GRAN BRETAGNA",
                    "GRECIA",
                    "GRENADA",
                    "GROENLANDIA",
                    "GUADALUPA",
                    "GUAM",
                    "GUATEMALA",
                    "GUIANA",
                    "GUIANA FRANCESE",
                    "GUINEA EQUATORIALE",
                    "GUINEA-BISSAU",
                    "HAITI",
                    "HONDURAS",
                    "HONG KONG",
                    "INDIA",
                    "INDONESIA",
                    "IRAN",
                    "IRAQ",
                    "IRLANDA",
                    "ISLANDA",
                    "ISOLA DI NORFOLK",
                    "ISOLE CHRISTMAS",
                    "ISOLE COCOS",
                    "ISOLE FAEROER",
                    "ISOLE FALKLAND",
                    "ISOLE FIGI",
                    "ISOLE MARIANNE",
                    "ISOLE MARSHALL",
                    "ISOLE MINORI (USA)",
                    "ISOLE PALAU",
                    "ISOLE SALOMONE",
                    "ISOLE TOKELAU",
                    "ISOLE VERGINI (BRIT",
                    "ISOLE VERGINI (USA)",
                    "ISRAELE",
                    "ITALIA",
                    "KAZAKISTAN",
                    "KENYA",
                    "KIRGHIZISTAN",
                    "KIRIBATI",
                    "KOSOVO",
                    "KUWAIT",
                    "LAOS",
                    "LESOTHO",
                    "LETTONIA",
                    "LIBANO",
                    "LIBERIA",
                    "LIBIA",
                    "LIECHTENSTEIN",
                    "LITUANIA",
                    "LUSSEMBURGO",
                    "MACAO",
                    "MACEDONIA",
                    "MADAGASCAR",
                    "MALAISIA",
                    "MALAWI",
                    "MALDIVE",
                    "MALI",
                    "MALTA",
                    "MAROCCO",
                    "MARTINICA",
                    "MAURITANIA",
                    "MAURIZIO",
                    "MAYOTTE",
                    "MESSICO",
                    "MOLDAVIA",
                    "MONGOLIA",
                    "MONTECARLO",
                    "MONTSERRAT",
                    "MOZAMBICO",
                    "MYANMAR (UNIONE)",
                    "NAMIBIA",
                    "NAURU",
                    "NEPAL",
                    "NICARAGUA",
                    "NIGER",
                    "NIGERIA",
                    "NORVEGIA",
                    "NUOVA CALEDONIA",
                    "NUOVA ZELANDA",
                    "OLANDA",
                    "OMAN",
                    "PAKISTAN",
                    "PANAMA",
                    "PAPUA-NUOVA GUINEA",
                    "PARAGUAY",
                    "PERU'",
                    "PITCAIRN",
                    "POLINESIA FRANCESE",
                    "POLONIA",
                    "PORTOGALLO",
                    "PORTORICO",
                    "QATAR",
                    "REP. DEMOCRATICA CO",
                    "REPUBBLICA CECA",
                    "REPUBBLICA DEL CONG",
                    "REPUBBLICA DI GUINE",
                    "REPUBBLICA DOMINICA",
                    "REPUBBLICA SLOVACCA",
                    "REUNION",
                    "ROMANIA",
                    "RUANDA",
                    "RUSSIA",
                    "SAINT VINCENT E GRE",
                    "SAINT-PIERRE ET MIQ",
                    "SALVADOR",
                    "SAMOA (USA)",
                    "SAMOA OCCIDENTALI",
                    "SAN CRISTOFORO E NE",
                    "SAN MARINO",
                    "SANTA LUCIA",
                    "SAO TOME' E PRINCIP",
                    "SENEGAL",
                    "SERBIA E MONTENEGRO",
                    "SEYCHELLES",
                    "SIERRA LEONE",
                    "SINGAPORE",
                    "SIRIA",
                    "SLOVENIA",
                    "SOMALIA",
                    "SPAGNA",
                    "SRI LANKA",
                    "STATI UNITI D'AMERI",
                    "SUD AFRICA",
                    "SUDAN",
                    "SURINAME",
                    "SVEZIA",
                    "SVIZZERA",
                    "SWAZILAND",
                    "TAGISKISTAN",
                    "TAILANDIA",
                    "TAIWAN",
                    "TANZANIA",
                    "TIMOR ORIENTALE",
                    "TOGO",
                    "TONGA",
                    "TRINIDAD E TOBAGO",
                    "TUNISIA",
                    "TURCHIA",
                    "TURKMENISTAN",
                    "TURKS E CAICOS",
                    "TUVALU",
                    "UCRAINA",
                    "UGANDA",
                    "UNGHERIA",
                    "UNIONE SOVIETICA",
                    "URUGUAY",
                    "UZBEKISTAN",
                    "VANUATU",
                    "VENEZUELA",
                    "VIETNAM",
                    "WALLIS E FUTUNA",
                    "YEMEN",
                    "ZAIRE",
                    "ZAMBIA",
                    "ZIMBABWE"
            };
        } else {
            listName = new String[]{"not need"};
        }
   }
}
