package com.mediscreen.service;

import com.mediscreen.beans.NoteBean;
import com.mediscreen.beans.PatientBean;
import com.mediscreen.constant.RiskControl;
import com.mediscreen.proxies.MicroserviceNoteProxy;
import com.mediscreen.proxies.MicroservicePatientProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class MediscreenService {

    private final MicroservicePatientProxy patientProxy;
    private final MicroserviceNoteProxy noteProxy;

    public String[] motCle = {"Hémoglobine A1C","Microalbumine","Taille","Poids","Fumeur","Anormal","Cholestérol","Vertige","Rechute","Réaction","Anticorps"};

    public int calculAge(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return Period.between(LocalDate.parse(simpleDateFormat.format(date), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now()).getYears();
    }

    public int determinateAge(Long patientId) {
        PatientBean patientBean = patientProxy.getPatient(patientId).getBody();
        return calculAge(patientBean.getDob());
    }

    public String determinateRisk(int age, int counter, char sex){
        String risk = RiskControl.NONE;

        if(age > RiskControl.AGE_LIMIT ) {
            if( counter >=  RiskControl.EARLY_ONSET_SUP_AGE_LIMIT ) {
                risk =  RiskControl.EARLY_ONSET;
            }
            else if( counter >=  RiskControl.IN_DANGER_SUP_AGE_LIMIT ) {
                risk =  RiskControl.IN_DANGER;
            }
            else if( counter >=  RiskControl.BORDERLINE_SUP_AGE_LIMIT) {
                risk =  RiskControl.BORDERLINE;
            }
        }
       else {
            if( sex == 'M' ) {
                if(counter >=  RiskControl.M_EARLY_ONSET_INF_AGE_LIMIT ) {
                    risk =  RiskControl.EARLY_ONSET;
                }
                else if(counter >=  RiskControl.M_IN_DANGER_INF_AGE_LIMIT ) {
                    risk =  RiskControl.IN_DANGER;
                }
            }
            else {
                if(counter >= RiskControl.F_EARLY_ONSET_INF_AGE_LIMIT) {
                    risk =  RiskControl.EARLY_ONSET;
                }
                else if(counter >= RiskControl.F_IN_DANGER_INF_AGE_LIMIT) {
                    risk =  RiskControl.IN_DANGER;
                }
            }
        }

        System.out.println("risk : " + risk);
        return risk;
    }

    public String calculateRisk(long patientId) {
        PatientBean patientBean = patientProxy.getPatient(patientId).getBody();
        ResponseEntity<List<NoteBean>> re = noteProxy.searchNote(patientBean.getId());
        List<NoteBean> noteBeanList = re.getBody();

        char sex = Character.toUpperCase(patientBean.getSex());
        int age = determinateAge(patientId);
        int counter = 0;
        for(NoteBean note:noteBeanList) {
            for(String value:motCle){
                String textNote;
                textNote = note.getNote().toUpperCase();
                if(textNote.contains(value.toUpperCase())) {
                    System.out.println(value);
                    counter += 1;
                }
            }
        }
        return determinateRisk(age, counter, sex);
    }

    //version qui exclu les doublons de motcle
    public String calulateRiskWithoutDouble(long patientId){
        PatientBean patientBean = patientProxy.getPatient(patientId).getBody();
        ResponseEntity<List<NoteBean>> re = noteProxy.searchNote(patientBean.getId());
        List<NoteBean> noteBeanList = re.getBody();

        char sex = Character.toUpperCase(patientBean.getSex());
        int age = determinateAge(patientId);
        int counter = 0;
        Set<String> set =  new HashSet<String>() ;

        for(NoteBean note:noteBeanList) {
            for(String value:motCle){
                String textNote;
                textNote = note.getNote().toUpperCase();
                if(textNote.contains(value.toUpperCase())) {
                    set.add(value);
                }
            }
        }
        System.out.println(set);
        counter = set.size();
        return determinateRisk(age, counter, sex);
    }
}
