package com.mediscreen.service;

import com.mediscreen.beans.NoteBean;
import com.mediscreen.beans.PatientBean;
import com.mediscreen.proxies.MicroserviceNoteProxy;
import com.mediscreen.proxies.MicroservicePatientProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class MediscreenService {

    @Autowired
    MicroservicePatientProxy patientProxy;

    @Autowired
    MicroserviceNoteProxy noteProxy;

    public String[] motCle = {"Hémoglobine A1C","Microalbumine","Taille","Poids","Fumeur","Anormal","Cholestérol","Vertige","Rechute","Réaction","Anticorps"};

    public int calculAge(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return Period.between(LocalDate.parse(simpleDateFormat.format(date), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now()).getYears();
    }

    public int determinateAge(Long patientId) {
        PatientBean patientBean = patientProxy.getPatient(patientId);
        return calculAge(patientBean.getDob());
    }

    public String determinateRisk(int age, int counter, char sex){
        String risk = "None";

        if(age > 30 ) {
            if( counter >= 2) {
                risk = "Borderline";
            }
            if( counter >= 6 ) {
                risk = "In Danger";
            }
            if( counter >= 8 ) {
                risk = "Early onset";
            }
        }

        if(age < 30) {
            if( sex == 'M' ) {
                if(counter >= 3 ) {
                    risk = "In Danger";
                }
                if(counter >= 5 ) {
                    risk = "Early onset";
                }
            }
            if(sex == 'F') {
                if(counter >= 4) {
                    risk = "In Danger";
                }
                if(counter >= 7) {
                    risk = "Early onset";
                }
            }
        }
        System.out.println("risk : " + risk);
        return risk;
    }

    public String calculateRisk(long patientId) {
        PatientBean patientBean = patientProxy.getPatient(patientId);
        List<NoteBean> noteBeanList = noteProxy.searchNote(patientBean.getId());

        char sex = Character.toUpperCase(patientBean.getSex());
        int age = determinateAge(patientId);
        int counter = 0;
        //todo voir pour faire un SET afin de filtrer les motcle dans les notes
        for(NoteBean note:noteBeanList) {
            for(String value:motCle){
                String textNote;
                textNote = note.getNote().toUpperCase();
                //todo voir ce qu'il fait des accents
                if(textNote.contains(value.toUpperCase())) {
                    counter += 1;
                }
            }
        }
        return determinateRisk(age, counter, sex);
    }
}
