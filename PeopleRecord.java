import java.util.Arrays;

class PeopleRecord implements MyData<PeopleRecord, String[]> {
    /*
     * this is a read-only data class once constructed
     */

    private String givenName, familyName, companyName, address, city,
            county, state, zip, phone1, phone2, email, web, birthday;

    PeopleRecord() { // record.length = 13
        givenName = "";
        familyName = "";
        companyName = "";
        address = "";
        city = "";
        county = "";
        state = "";
        zip = "";
        phone1 = "";
        phone2 = "";
        email = "";
        web = "";
        birthday = "";
    }

    PeopleRecord(String[] record) { // record.length = 13
        givenName = record[0];
        familyName = record[1];
        companyName = record[2];
        address = record[3];
        city = record[4];
        county = record[5];
        state = record[6];
        zip = record[7];
        phone1 = record[8];
        phone2 = record[9];
        email = record[10];
        web = record[11];
        birthday = record[12];
    }

    public String getGivenName() { // getters * 13
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getEmail() {
        return email;
    }

    public String getWeb() {
        return web;
    }

    public String getBirthday() {
        return birthday;
    }

    public int compareTo(PeopleRecord other) {
        if (familyName.compareTo(other.familyName) > 0) // family name first
            return 1;
        else if (familyName.compareTo(other.familyName) < 0)
            return -1;
        else {
            if (givenName.compareTo(other.givenName) > 0) // given name second
                return 1;
            else if (givenName.compareTo(other.givenName) < 0)
                return -1;
            else { // Support additional ways of comparing
                if (other.email == null)
                    return 0;
                if (email.compareTo(other.email) > 0) // email in case
                    return 1;
                else if (email.compareTo(other.email) < 0)
                    return -1;
                else
                    return 0;
            }
        }
    }

    public int match(String[] names) {
        PeopleRecord people = new PeopleRecord(Arrays.copyOf(names, 13));
        return compareTo(people);
    }

    public String toString() {
        return "[" + givenName + ", " + familyName + ", " + companyName + ", "
                + address + ", " + city + ", " + county + ", " + state + ", "
                + zip + ", " + phone1 + ", " + phone2 + ", " + email + ", "
                + web + ", " + birthday + "]";
    }
}