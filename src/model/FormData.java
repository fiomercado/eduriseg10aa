package model;

public class FormData {
    // Personal Information
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String birthMonth;
    private String birthDay;
    private String birthYear;
    private String age;
    private String sex;
    private String civilStatus;
    private String religion;
    private String citizenship;
    private String address;
    private String permanentAddress;
    private String houseType;
    private String income;
    private String contact;
    private String email;
    private String cellphone;
    private String gwa;
    private String degreeProgram;
    private String schoolName;
    private String schoolAddress;
    private String schoolEmail;
    private String schoolId;

    // Parent Information
    private String parentType;
    private String parentFirstName;
    private String parentMiddleName;
    private String parentLastName;
    private String parentSuffix;
    private String parentBirthMonth;
    private String parentBirthDay;
    private String parentBirthYear;
    private String parentAge;
    private String parentSex;
    private String parentCitizenship;
    private String parentCivilStatus;
    private String parentOccupation;
    private String parentEmail;
    private String parentMonthlyIncome;
    private String parentEducationalAttainment;

    // Legacy parent fields (keeping for compatibility)
    private String parentName;
    private String parentContact;
    private String parentAddress;
    private String parentRelation;

    // Child Information
    private String childFirstName;
    private String childMiddleName;
    private String childLastName;
    private String childSuffix;
    private String childBirthMonth;
    private String childBirthDay;
    private String childBirthYear;
    private String childAge;
    private String childSex;
    private String childGradeLevel;
    private String childSchoolName;
    private String childSchoolAddress;

    private String childName;
    private String childGrade;
    private String childSchool;


    public FormData() {}

    // Personal Information Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSuffix() { return suffix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }

    public String getBirthMonth() { return birthMonth; }
    public void setBirthMonth(String birthMonth) { this.birthMonth = birthMonth; }

    public String getBirthDay() { return birthDay; }
    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getBirthYear() { return birthYear; }
    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getCivilStatus() { return civilStatus; }
    public void setCivilStatus(String civilStatus) { this.civilStatus = civilStatus; }

    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }

    public String getCitizenship() { return citizenship; }
    public void setCitizenship(String citizenship) { this.citizenship = citizenship; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }

    public String getHouseType() { return houseType; }
    public void setHouseType(String houseType) { this.houseType = houseType; }

    public String getIncome() { return income; }
    public void setIncome(String income) { this.income = income; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCellphone() { return cellphone; }
    public void setCellphone(String cellphone) { this.cellphone = cellphone; }

    public String getGwa() { return gwa; }
    public void setGwa(String gwa) { this.gwa = gwa; }

    public String getDegreeProgram() { return degreeProgram; }
    public void setDegreeProgram(String degreeProgram) { this.degreeProgram = degreeProgram; }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    public String getSchoolAddress() { return schoolAddress; }
    public void setSchoolAddress(String schoolAddress) { this.schoolAddress = schoolAddress; }

    public String getSchoolEmail() { return schoolEmail; }
    public void setSchoolEmail(String schoolEmail) { this.schoolEmail = schoolEmail; }

    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }

    // Parent Information Getters and Setters
    public String getParentType() { return parentType; }
    public void setParentType(String parentType) { this.parentType = parentType; }

    public String getParentFirstName() { return parentFirstName; }
    public void setParentFirstName(String parentFirstName) { this.parentFirstName = parentFirstName; }

    public String getParentMiddleName() { return parentMiddleName; }
    public void setParentMiddleName(String parentMiddleName) { this.parentMiddleName = parentMiddleName; }

    public String getParentLastName() { return parentLastName; }
    public void setParentLastName(String parentLastName) { this.parentLastName = parentLastName; }

    public String getParentSuffix() { return parentSuffix; }
    public void setParentSuffix(String parentSuffix) { this.parentSuffix = parentSuffix; }

    public String getParentBirthMonth() { return parentBirthMonth; }
    public void setParentBirthMonth(String parentBirthMonth) { this.parentBirthMonth = parentBirthMonth; }

    public String getParentBirthDay() { return parentBirthDay; }
    public void setParentBirthDay(String parentBirthDay) { this.parentBirthDay = parentBirthDay; }

    public String getParentBirthYear() { return parentBirthYear; }
    public void setParentBirthYear(String parentBirthYear) { this.parentBirthYear = parentBirthYear; }

    public String getParentAge() { return parentAge; }
    public void setParentAge(String parentAge) { this.parentAge = parentAge; }

    public String getParentSex() { return parentSex; }
    public void setParentSex(String parentSex) { this.parentSex = parentSex; }

    public String getParentCitizenship() { return parentCitizenship; }
    public void setParentCitizenship(String parentCitizenship) { this.parentCitizenship = parentCitizenship; }

    public String getParentCivilStatus() { return parentCivilStatus; }
    public void setParentCivilStatus(String parentCivilStatus) { this.parentCivilStatus = parentCivilStatus; }

    public String getParentOccupation() { return parentOccupation; }
    public void setParentOccupation(String parentOccupation) { this.parentOccupation = parentOccupation; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public String getParentMonthlyIncome() { return parentMonthlyIncome; }
    public void setParentMonthlyIncome(String parentMonthlyIncome) { this.parentMonthlyIncome = parentMonthlyIncome; }

    public String getParentEducationalAttainment() { return parentEducationalAttainment; }
    public void setParentEducationalAttainment(String parentEducationalAttainment) { this.parentEducationalAttainment = parentEducationalAttainment; }

    // Legacy parent getters and setters
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentContact() { return parentContact; }
    public void setParentContact(String parentContact) { this.parentContact = parentContact; }

    public String getParentAddress() { return parentAddress; }
    public void setParentAddress(String parentAddress) { this.parentAddress = parentAddress; }

    public String getParentRelation() { return parentRelation; }
    public void setParentRelation(String parentRelation) { this.parentRelation = parentRelation; }

    // Child Information Getters and Setters
    public String getChildFirstName() { return childFirstName; }
    public void setChildFirstName(String childFirstName) { this.childFirstName = childFirstName; }

    public String getChildMiddleName() { return childMiddleName; }
    public void setChildMiddleName(String childMiddleName) { this.childMiddleName = childMiddleName; }

    public String getChildLastName() { return childLastName; }
    public void setChildLastName(String childLastName) { this.childLastName = childLastName; }

    public String getChildSuffix() { return childSuffix; }
    public void setChildSuffix(String childSuffix) { this.childSuffix = childSuffix; }

    public String getChildBirthMonth() { return childBirthMonth; }
    public void setChildBirthMonth(String childBirthMonth) { this.childBirthMonth = childBirthMonth; }

    public String getChildBirthDay() { return childBirthDay; }
    public void setChildBirthDay(String childBirthDay) { this.childBirthDay = childBirthDay; }

    public String getChildBirthYear() { return childBirthYear; }
    public void setChildBirthYear(String childBirthYear) { this.childBirthYear = childBirthYear; }

    public String getChildAge() { return childAge; }
    public void setChildAge(String childAge) { this.childAge = childAge; }

    public String getChildSex() { return childSex; }
    public void setChildSex(String childSex) { this.childSex = childSex; }

    public String getChildGradeLevel() { return childGradeLevel; }
    public void setChildGradeLevel(String childGradeLevel) { this.childGradeLevel = childGradeLevel; }

    public String getChildSchoolName() { return childSchoolName; }
    public void setChildSchoolName(String childSchoolName) { this.childSchoolName = childSchoolName; }

    public String getChildSchoolAddress() { return childSchoolAddress; }
    public void setChildSchoolAddress(String childSchoolAddress) { this.childSchoolAddress = childSchoolAddress; }

    // Legacy child getters and setters
    public String getChildName() { return childName; }
    public void setChildName(String childName) { this.childName = childName; }

    public String getChildGrade() { return childGrade; }
    public void setChildGrade(String childGrade) { this.childGrade = childGrade; }

    public String getChildSchool() { return childSchool; }
    public void setChildSchool(String childSchool) { this.childSchool = childSchool; }

    @Override
    public String toString() {
        return "FormData{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                ", email='" + email + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", degreeProgram='" + degreeProgram + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", parentType='" + parentType + '\'' +
                ", parentFirstName='" + parentFirstName + '\'' +
                ", parentLastName='" + parentLastName + '\'' +
                ", childFirstName='" + childFirstName + '\'' +
                ", childLastName='" + childLastName + '\'' +
                '}';
    }
}