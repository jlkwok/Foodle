package com.swe.foodle;

import java.util.HashMap;

/**
 * Retrieves the user's location.
 */
public class UserLocation {
    // stores the latitude of the user's location
    private double latitude;
    // stores the longitude of the user's location
    private double longitude;
    // stores the country linked to the user's location
    private String countryCode;
    // stores the cuisine type associated with the country
    private CuisineType cuisineType;
    // stores a mapping from the country to the cuisine type
    private HashMap<String, CuisineType> countryToCuisineCache = new HashMap<>();
    {
        // Afghanistan
        countryToCuisineCache.put("AF", CuisineType.MiddleEastern);
        // Aland Islands
        countryToCuisineCache.put("AX", CuisineType.Nordic);
        // Albania
        countryToCuisineCache.put("AL", CuisineType.EasternEuropean);
        // Algeria
        countryToCuisineCache.put("DZ", CuisineType.African);
        // American Samoa
        countryToCuisineCache.put("AS", CuisineType.Australian);
        // Andorra
        countryToCuisineCache.put("AD", CuisineType.African);
        // Angola
        countryToCuisineCache.put("AO", CuisineType.African);
        // Anguilla
        countryToCuisineCache.put("AI", CuisineType.Caribbean);
        // Antarctica - no cuisine
        countryToCuisineCache.put("AQ", CuisineType.Nordic);
        // Antigua and Barbuda
        countryToCuisineCache.put("AG", CuisineType.LatinAmerican);
        // Argentina
        countryToCuisineCache.put("AR", CuisineType.LatinAmerican);
        // Armenia
        countryToCuisineCache.put("AM", CuisineType.EasternEuropean);
        // Aruba
        countryToCuisineCache.put("AW", CuisineType.Caribbean);
        // Australia
        countryToCuisineCache.put("AU", CuisineType.Australian);
        // Austria
        countryToCuisineCache.put("AT", CuisineType.German);
        // Azerbaijan
        countryToCuisineCache.put("AZ", CuisineType.EasternEuropean);
        // Bahrain
        countryToCuisineCache.put("BH", CuisineType.MiddleEastern);
        // Bahamas
        countryToCuisineCache.put("BS", CuisineType.Caribbean);
        // Bangladesh
        countryToCuisineCache.put("BD", CuisineType.Indian);
        // Barbados
        countryToCuisineCache.put("BB", CuisineType.Caribbean);
        // Belarus
        countryToCuisineCache.put("BY", CuisineType.EasternEuropean);
        // Belgium
        countryToCuisineCache.put("BE", CuisineType.German);
        // Belize
        countryToCuisineCache.put("BZ", CuisineType.LatinAmerican);
        // Benin
        countryToCuisineCache.put("BJ", CuisineType.African);
        // Bermuda
        countryToCuisineCache.put("BM", CuisineType.Caribbean);
        // Bhutan
        countryToCuisineCache.put("BT", CuisineType.Indian);
        // Bolivia
        countryToCuisineCache.put("BO", CuisineType.LatinAmerican);
        // Bonaire
        countryToCuisineCache.put("BQ", CuisineType.LatinAmerican);
        // Bosnia and Herzegovina
        countryToCuisineCache.put("BA", CuisineType.EasternEuropean);
        // Botswana
        countryToCuisineCache.put("BW", CuisineType.African);
        // Bouvet Island
        countryToCuisineCache.put("BV", CuisineType.Nordic);
        // Brazil
        countryToCuisineCache.put("BR", CuisineType.LatinAmerican);
        // British Indian Ocean Territory
        countryToCuisineCache.put("IO", CuisineType.Indian);
        // Brunei Darussalum
        countryToCuisineCache.put("BN", CuisineType.Chinese);
        // Bulgaria
        countryToCuisineCache.put("BG", CuisineType.EasternEuropean);
        // Burkina Faso
        countryToCuisineCache.put("BF", CuisineType.African);
        // Burundi
        countryToCuisineCache.put("BI", CuisineType.African);
        // Cambodia
        countryToCuisineCache.put("KH", CuisineType.Chinese);
        // Cameroon
        countryToCuisineCache.put("CM", CuisineType.African);
        // Canada
        countryToCuisineCache.put("CA", CuisineType.American);
        // Cape Verde
        countryToCuisineCache.put("CV", CuisineType.African);
        // Cayman Islands
        countryToCuisineCache.put("KY", CuisineType.LatinAmerican);
        // Central African Republic
        countryToCuisineCache.put("CF", CuisineType.African);
        // Chad
        countryToCuisineCache.put("TD", CuisineType.African);
        // Chile
        countryToCuisineCache.put("CL", CuisineType.LatinAmerican);
        // China
        countryToCuisineCache.put("CN", CuisineType.Chinese);
        // Christmas Island
        countryToCuisineCache.put("CX", CuisineType.Australian);
        // Cocos (Keeling) Islands
        countryToCuisineCache.put("CC", CuisineType.Australian);
        // Colombia
        countryToCuisineCache.put("CO", CuisineType.LatinAmerican);
        // Comoros
        countryToCuisineCache.put("KM", CuisineType.African);
        // Congo
        countryToCuisineCache.put("CG", CuisineType.African);
        // Congo, the Democratic Republic of the
        countryToCuisineCache.put("CD", CuisineType.African);
        // Cook Islands
        countryToCuisineCache.put("CK", CuisineType.Australian);
        // Costa Rica
        countryToCuisineCache.put("CR", CuisineType.LatinAmerican);
        // Cote d'Ivoire
        countryToCuisineCache.put("CI", CuisineType.African);
        // Croatia
        countryToCuisineCache.put("HR", CuisineType.EasternEuropean);
        // Cuba
        countryToCuisineCache.put("CU", CuisineType.LatinAmerican);
        // Curacao
        countryToCuisineCache.put("CW", CuisineType.Caribbean);
        // Cyprus
        countryToCuisineCache.put("CY", CuisineType.Greek);
        // Czech Republic
        countryToCuisineCache.put("CZ", CuisineType.German);
        // Denmark
        countryToCuisineCache.put("DK", CuisineType.Nordic);
        // Djibouti
        countryToCuisineCache.put("DJ", CuisineType.African);
        // Dominica
        countryToCuisineCache.put("DM", CuisineType.Caribbean);
        // Dominican Republic
        countryToCuisineCache.put("DO", CuisineType.Caribbean);
        // Ecuador
        countryToCuisineCache.put("EC", CuisineType.LatinAmerican);
        // Egypt
        countryToCuisineCache.put("EG", CuisineType.MiddleEastern);
        // El Salvador
        countryToCuisineCache.put("SV", CuisineType.LatinAmerican);
        // Equatorial Guinea
        countryToCuisineCache.put("GQ", CuisineType.African);
        // Eritrea
        countryToCuisineCache.put("ER", CuisineType.African);
        // Estonia
        countryToCuisineCache.put("EE", CuisineType.EasternEuropean);
        // Ethiopia
        countryToCuisineCache.put("ET", CuisineType.African);
        // Falkland Islands
        countryToCuisineCache.put("FK", CuisineType.LatinAmerican);
        // Fiji
        countryToCuisineCache.put("FJ", CuisineType.Australian);
        // Finland
        countryToCuisineCache.put("FI", CuisineType.Nordic);
        // France
        countryToCuisineCache.put("FR", CuisineType.French);
        // French Guiana
        countryToCuisineCache.put("GF", CuisineType.French);
        // French Polynesia
        countryToCuisineCache.put("PF", CuisineType.French);
        // French Southern Territories
        countryToCuisineCache.put("TF", CuisineType.French);
        // Gabon
        countryToCuisineCache.put("GA", CuisineType.African);
        // Gambia
        countryToCuisineCache.put("GM", CuisineType.African);
        // Georgia
        countryToCuisineCache.put("GE", CuisineType.MiddleEastern);
        // Germany
        countryToCuisineCache.put("DE", CuisineType.German);
        // Ghana
        countryToCuisineCache.put("GH", CuisineType.African);
        // Gibraltar
        countryToCuisineCache.put("GI", CuisineType.Spanish);
        // Greece
        countryToCuisineCache.put("GR", CuisineType.Greek);
        // Greenland
        countryToCuisineCache.put("GL", CuisineType.Nordic);
        // Grenada
        countryToCuisineCache.put("GD", CuisineType.LatinAmerican);
        // Guadeloupe
        countryToCuisineCache.put("GP", CuisineType.Caribbean);
        // Guam
        countryToCuisineCache.put("GU", CuisineType.American);
        // Guatemala
        countryToCuisineCache.put("GT", CuisineType.LatinAmerican);
        // Guernsey
        countryToCuisineCache.put("GG", CuisineType.French);
        // Guinea
        countryToCuisineCache.put("GN", CuisineType.African);
        // Guinea-Bissau
        countryToCuisineCache.put("GW", CuisineType.African);
        // Guyana
        countryToCuisineCache.put("GY", CuisineType.African);
        // Haiti
        countryToCuisineCache.put("HT", CuisineType.Caribbean);
        // Heard Island and McDonald Islands
        countryToCuisineCache.put("HM", CuisineType.Australian);
        // Holy See (vatican City State)
        countryToCuisineCache.put("VA", CuisineType.Italian);
        // Honduras
        countryToCuisineCache.put("HN", CuisineType.LatinAmerican);
        // Hong Kong
        countryToCuisineCache.put("HK", CuisineType.Chinese);
        // Hungary
        countryToCuisineCache.put("HU", CuisineType.MiddleEastern);
        // Iceland
        countryToCuisineCache.put("IS", CuisineType.Nordic);
        // India
        countryToCuisineCache.put("IN", CuisineType.Indian);
        // Indonesia
        countryToCuisineCache.put("ID", CuisineType.Chinese);
        // Iran
        countryToCuisineCache.put("IR", CuisineType.MiddleEastern);
        // Iraq
        countryToCuisineCache.put("IQ", CuisineType.MiddleEastern);
        // Ireland
        countryToCuisineCache.put("IE", CuisineType.Irish);
        // Isle of Man
        countryToCuisineCache.put("IM", CuisineType.Irish);
        // Israel
        countryToCuisineCache.put("IL", CuisineType.MiddleEastern);
        // Italy
        countryToCuisineCache.put("IT", CuisineType.Italian);
        // Jamaica
        countryToCuisineCache.put("JM", CuisineType.Caribbean);
        // Japan
        countryToCuisineCache.put("JP", CuisineType.Japanese);
        // Jersey
        countryToCuisineCache.put("JE", CuisineType.French);
        // Jordan
        countryToCuisineCache.put("JO", CuisineType.MiddleEastern);
        // Kazakhstan
        countryToCuisineCache.put("KZ", CuisineType.MiddleEastern);
        // Kenya
        countryToCuisineCache.put("KE", CuisineType.African);
        // Kiribati
        countryToCuisineCache.put("KI", CuisineType.Australian);
        // Korea - Democratic People's Republic of
        countryToCuisineCache.put("KP", CuisineType.Korean);
        // Korea, Republic of
        countryToCuisineCache.put("KR", CuisineType.Korean);
        // Kuwait
        countryToCuisineCache.put("KW", CuisineType.MiddleEastern);
        // Kyrgyzstan
        countryToCuisineCache.put("KG", CuisineType.MiddleEastern);
        // Lao people's Democratic Republic
        countryToCuisineCache.put("LA", CuisineType.Indian);
        // Latvia
        countryToCuisineCache.put("LV", CuisineType.EasternEuropean);
        // Lebanon
        countryToCuisineCache.put("LB", CuisineType.MiddleEastern);
        // Lesotho
        countryToCuisineCache.put("LS", CuisineType.African);
        // Liberia
        countryToCuisineCache.put("LR", CuisineType.African);
        // Libya
        countryToCuisineCache.put("LY", CuisineType.African);
        // Liechtenstein
        countryToCuisineCache.put("LI", CuisineType.German);
        // Lithuania
        countryToCuisineCache.put("LT", CuisineType.German);
        // Luxembourg
        countryToCuisineCache.put("LU", CuisineType.German);
        // Macao
        countryToCuisineCache.put("MO", CuisineType.Chinese);
        // Macedonia
        countryToCuisineCache.put("MK", CuisineType.Greek);
        // Madagascar
        countryToCuisineCache.put("MG", CuisineType.African);
        // Malawi
        countryToCuisineCache.put("MW", CuisineType.African);
        // Malayasia
        countryToCuisineCache.put("MY", CuisineType.Chinese);
        // Maldives
        countryToCuisineCache.put("MV", CuisineType.Indian);
        // Mali
        countryToCuisineCache.put("ML", CuisineType.African);
        // Malta
        countryToCuisineCache.put("MT", CuisineType.Italian);
        // Marshall Islands
        countryToCuisineCache.put("MH", CuisineType.Australian);
        // Martinique
        countryToCuisineCache.put("MQ", CuisineType.Caribbean);
        // Mauritania
        countryToCuisineCache.put("MR", CuisineType.African);
        // Mauritius
        countryToCuisineCache.put("MU", CuisineType.African);
        // Mayotte
        countryToCuisineCache.put("YT", CuisineType.African);
        // Mexico
        countryToCuisineCache.put("MX", CuisineType.Mexican);
        // Micronesia
        countryToCuisineCache.put("FM", CuisineType.Australian);
        // Moldova
        countryToCuisineCache.put("MD", CuisineType.EasternEuropean);
        // Monaco
        countryToCuisineCache.put("MC", CuisineType.French);
        // Mongolia
        countryToCuisineCache.put("MN", CuisineType.Chinese);
        // Montenegro
        countryToCuisineCache.put("ME", CuisineType.MiddleEastern);
        // Montserrat
        countryToCuisineCache.put("MS", CuisineType.Caribbean);
        // Morocco
        countryToCuisineCache.put("MA", CuisineType.African);
        // Mozambique
        countryToCuisineCache.put("MZ", CuisineType.African);
        // Myanmar
        countryToCuisineCache.put("MM", CuisineType.Chinese);
        // Namibia
        countryToCuisineCache.put("NA", CuisineType.African);
        // Nauru
        countryToCuisineCache.put("NR", CuisineType.Australian);
        // Nepal
        countryToCuisineCache.put("NP", CuisineType.Chinese);
        // Netherlands
        countryToCuisineCache.put("NL", CuisineType.German);
        // New Caledonia
        countryToCuisineCache.put("NC", CuisineType.Australian);
        // New Zealand
        countryToCuisineCache.put("NZ", CuisineType.Australian);
        // Nicaragua
        countryToCuisineCache.put("NI", CuisineType.Mexican);
        // Niger
        countryToCuisineCache.put("NE", CuisineType.African);
        // Nigeria
        countryToCuisineCache.put("NG", CuisineType.African);
        // Niue
        countryToCuisineCache.put("NU", CuisineType.Australian);
        // Norfolk Island
        countryToCuisineCache.put("NF", CuisineType.Australian);
        // Northern Mariana Islands
        countryToCuisineCache.put("MP", CuisineType.Australian);
        // Norway
        countryToCuisineCache.put("NO", CuisineType.Nordic);
        // Oman
        countryToCuisineCache.put("OM", CuisineType.MiddleEastern);
        // Pakistan
        countryToCuisineCache.put("PK", CuisineType.MiddleEastern);
        // Palau
        countryToCuisineCache.put("PW", CuisineType.Australian);
        // Palestine
        countryToCuisineCache.put("PS", CuisineType.MiddleEastern);
        // Panama
        countryToCuisineCache.put("PA", CuisineType.LatinAmerican);
        // Papua New Guinea
        countryToCuisineCache.put("PG", CuisineType.Australian);
        // Paraguay
        countryToCuisineCache.put("PY", CuisineType.LatinAmerican);
        // Peru
        countryToCuisineCache.put("PE", CuisineType.LatinAmerican);
        // Philippines
        countryToCuisineCache.put("PH", CuisineType.Chinese);
        // Pitcairn
        countryToCuisineCache.put("PN", CuisineType.LatinAmerican);
        // Poland
        countryToCuisineCache.put("PL", CuisineType.German);
        // Portugal
        countryToCuisineCache.put("PT", CuisineType.Spanish);
        // Puerto Rico
        countryToCuisineCache.put("PR", CuisineType.Mexican);
        // Qatar
        countryToCuisineCache.put("QA", CuisineType.MiddleEastern);
        // Reunion
        countryToCuisineCache.put("RE", CuisineType.African);
        // Romania
        countryToCuisineCache.put("RO", CuisineType.EasternEuropean);
        // Russian Federation
        countryToCuisineCache.put("RU", CuisineType.EasternEuropean);
        // Rwanda
        countryToCuisineCache.put("RW", CuisineType.African);
        // Saint Barthelemy
        countryToCuisineCache.put("BL", CuisineType.Caribbean);
        // Saint Helena
        countryToCuisineCache.put("SH", CuisineType.African);
        // Saint Kitts and Nevis
        countryToCuisineCache.put("KN", CuisineType.Caribbean);
        // Saint Lucia
        countryToCuisineCache.put("LC", CuisineType.Caribbean);
        // Saint Martin
        countryToCuisineCache.put("MF", CuisineType.Caribbean);
        // Saint Pierre and Miquelon
        countryToCuisineCache.put("PM", CuisineType.French);
        // Saint Vincent and the Grenadines
        countryToCuisineCache.put("VC", CuisineType.Caribbean);
        // Samoa
        countryToCuisineCache.put("WS", CuisineType.Australian);
        // San Marino
        countryToCuisineCache.put("SM", CuisineType.Italian);
        // Sao Tome and Principe
        countryToCuisineCache.put("ST", CuisineType.African);
        // Saudi Arabia
        countryToCuisineCache.put("SA", CuisineType.MiddleEastern);
        // Senegal
        countryToCuisineCache.put("SN", CuisineType.African);
        // Serbia
        countryToCuisineCache.put("RS", CuisineType.EasternEuropean);
        // Seychelles
        countryToCuisineCache.put("SC", CuisineType.African);
        // Sierra Leone
        countryToCuisineCache.put("SL", CuisineType.African);
        // Singapore
        countryToCuisineCache.put("SG", CuisineType.Chinese);
        // Saint Maarten (Dutch part)
        countryToCuisineCache.put("SX", CuisineType.Caribbean);
        // Slovakia
        countryToCuisineCache.put("SK", CuisineType.EasternEuropean);
        // Slovenia
        countryToCuisineCache.put("SI", CuisineType.MiddleEastern);
        // Solomon Islands
        countryToCuisineCache.put("SB", CuisineType.Australian);
        // Somalia
        countryToCuisineCache.put("SO", CuisineType.African);
        // South Africa
        countryToCuisineCache.put("ZA", CuisineType.African);
        // South Georgia and the South Sandwich Islands
        countryToCuisineCache.put("GS", CuisineType.LatinAmerican);
        // South Sudan
        countryToCuisineCache.put("SS", CuisineType.MiddleEastern);
        // Spain
        countryToCuisineCache.put("ES", CuisineType.Spanish);
        // Sri Lanka
        countryToCuisineCache.put("LK", CuisineType.Indian);
        // Sudan
        countryToCuisineCache.put("SD", CuisineType.MiddleEastern);
        // Suriname
        countryToCuisineCache.put("SR", CuisineType.LatinAmerican);
        // Svalbard and Jan Mayen
        countryToCuisineCache.put("SJ", CuisineType.Nordic);
        // Swaziland
        countryToCuisineCache.put("SZ", CuisineType.African);
        // Sweden
        countryToCuisineCache.put("SE", CuisineType.Nordic);
        // Switzerland
        countryToCuisineCache.put("CH", CuisineType.French);
        // Syrian Arab Republic
        countryToCuisineCache.put("SY", CuisineType.MiddleEastern);
        // Taiwan
        countryToCuisineCache.put("TW", CuisineType.Chinese);
        // Tajikistan
        countryToCuisineCache.put("TJ", CuisineType.MiddleEastern);
        // Tanzania
        countryToCuisineCache.put("TZ", CuisineType.African);
        // Thailand
        countryToCuisineCache.put("TH", CuisineType.Thai);
        // Timor-Leste
        countryToCuisineCache.put("TL", CuisineType.Thai);
        // Togo
        countryToCuisineCache.put("TG", CuisineType.African);
        // Tokelau
        countryToCuisineCache.put("TK", CuisineType.Australian);
        // Tonga
        countryToCuisineCache.put("TO", CuisineType.Australian);
        // Trinidad and Tobago
        countryToCuisineCache.put("TT", CuisineType.LatinAmerican);
        // Tunisia
        countryToCuisineCache.put("TN", CuisineType.African);
        // Turkey
        countryToCuisineCache.put("TR", CuisineType.MiddleEastern);
        // Turkmenistan
        countryToCuisineCache.put("TM", CuisineType.MiddleEastern);
        // Turks and Caicos Islands
        countryToCuisineCache.put("TC", CuisineType.MiddleEastern);
        // Tuvalu
        countryToCuisineCache.put("TV", CuisineType.Australian);
        // Uganda
        countryToCuisineCache.put("UG", CuisineType.African);
        // Ukraine
        countryToCuisineCache.put("UA", CuisineType.EasternEuropean);
        // United Arab Emirates
        countryToCuisineCache.put("AE", CuisineType.MiddleEastern);
        // United Kingdom
        countryToCuisineCache.put("GB", CuisineType.British);
        // United States
        countryToCuisineCache.put("US", CuisineType.American);
        // United States Minor Outlying Islands
        countryToCuisineCache.put("UM", CuisineType.American);
        // Uruguay
        countryToCuisineCache.put("UY", CuisineType.LatinAmerican);
        // Uzbekistan
        countryToCuisineCache.put("UZ", CuisineType.MiddleEastern);
        // Vanuatu
        countryToCuisineCache.put("VU", CuisineType.Australian);
        // Venezuela
        countryToCuisineCache.put("VE", CuisineType.LatinAmerican);
        // Vietnam
        countryToCuisineCache.put("VN", CuisineType.Vietnamese);
        // Virgin Islands, British
        countryToCuisineCache.put("VG", CuisineType.Caribbean);
        // Virgin Islands, US
        countryToCuisineCache.put("VI", CuisineType.Caribbean);
        // Wallis and Futuna
        countryToCuisineCache.put("WF", CuisineType.Australian);
        // Western Sahara
        countryToCuisineCache.put("EH", CuisineType.African);
        // Yemen
        countryToCuisineCache.put("YE", CuisineType.MiddleEastern);
        // Zambia
        countryToCuisineCache.put("ZM", CuisineType.African);
        // Zimbabwe
        countryToCuisineCache.put("ZW", CuisineType.African);

    }
    // stores the singleton pattern of UserLocation
    private static UserLocation instance = new UserLocation();

    /**
     * Creates a default UserLocation.
     */
    private UserLocation(){

    }

    /**
     * @return the single instance of UserLocation
     */
    public static UserLocation getInstance(){
        return instance;
    }

    /**
     * @return the country code of the user's location
     */
    public String getCountryCode(){
        return this.countryCode;
    }

    /**
     * Sets the country code to that specified
     * @param countryCode the country code to be set
     */
    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }

    /**
     * @return the cuisine type associated with the user's country code
     */
    public CuisineType getCuisineType(){
        if (countryToCuisineCache.containsKey(getCountryCode())){
            return countryToCuisineCache.get(getCountryCode());
        }
        else{
            return CuisineType.Italian;
        }
    }
}
