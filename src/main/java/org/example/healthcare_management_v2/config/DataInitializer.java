package org.example.healthcare_management_v2.config;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.config.dto_config.DoctorInfoDTO;
import org.example.healthcare_management_v2.config.dto_config.UserCreationDTO;
import org.example.healthcare_management_v2.entities.*;

import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.enums.Gender;
import org.example.healthcare_management_v2.enums.Status;
import org.example.healthcare_management_v2.repositories.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;

@Component
@AllArgsConstructor
public class DataInitializer {
    private final DoctorRepo doctorRepo;
    private final ClinicRepo clinicRepo;
    private final TimeSlotRepo timeSlotRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PatientStatusRepo patientStatusrepo;
    private final SpecializationRepo specializationrepo;
    private final ConsultationRepo consultationRepo;
    private final MedicationRepo medicationRepo;
    private final DataSetupService dataSetupService;

    @PostConstruct
    @Transactional
    public void init() {


        if (timeSlotRepo.count() == 0) {
            TimeSlot timeSlot_1 = TimeSlot.builder()
                    .startAt(LocalTime.of(7, 30))
                    .endAt(LocalTime.of(8, 30))
                    .build();

            TimeSlot timeSlot_2 = TimeSlot.builder()
                    .startAt(LocalTime.of(8, 30))
                    .endAt(LocalTime.of(9, 30))
                    .build();

            TimeSlot timeSlot_3 = TimeSlot.builder()
                    .startAt(LocalTime.of(9, 30))
                    .endAt(LocalTime.of(10, 30))
                    .build();

            TimeSlot timeSlot_4 = TimeSlot.builder()
                    .startAt(LocalTime.of(10, 30))
                    .endAt(LocalTime.of(11, 30))
                    .build();

            TimeSlot timeSlot_5 = TimeSlot.builder()
                    .startAt(LocalTime.of(13, 30))
                    .endAt(LocalTime.of(14, 30))
                    .build();

            TimeSlot timeSlot_6 = TimeSlot.builder()
                    .startAt(LocalTime.of(14, 30))
                    .endAt(LocalTime.of(15, 30))
                    .build();

            TimeSlot timeSlot_7 = TimeSlot.builder()
                    .startAt(LocalTime.of(15, 30))
                    .endAt(LocalTime.of(16, 30))
                    .build();

            TimeSlot timeSlot_8 = TimeSlot.builder()
                    .startAt(LocalTime.of(16, 30))
                    .endAt(LocalTime.of(17, 30))
                    .build();

            timeSlotRepo.save(timeSlot_1);
            timeSlotRepo.save(timeSlot_2);
            timeSlotRepo.save(timeSlot_3);
            timeSlotRepo.save(timeSlot_4);
            timeSlotRepo.save(timeSlot_5);
            timeSlotRepo.save(timeSlot_6);
            timeSlotRepo.save(timeSlot_7);
            timeSlotRepo.save(timeSlot_8);
        }

        if (roleRepo.count() == 0) {
            roleRepo.save(new Role(EnumRole.ADMIN.getRoleName(), "Admin role"));
            roleRepo.save(new Role(EnumRole.DOCTOR.getRoleName(), "Doctor role"));
            roleRepo.save(new Role(EnumRole.PATIENT.getRoleName(), "Patient role"));
            roleRepo.save(new Role(EnumRole.RECEPTIONIST.getRoleName(), "Receptionist role"));
        }

        if (patientStatusrepo.count() == 0) {
            // đang điều trị
            patientStatusrepo.save(new PatientStatus("Under treatment", "Patient is treatment"));
            // ra viện
            patientStatusrepo.save(new PatientStatus("Discharged", "Patient is Discharged"));
            // theo dõi chặt chẽ
            patientStatusrepo.save(new PatientStatus("Monitor", "Patient is Monitor"));
            // theo dõi bình thường
            patientStatusrepo.save(new PatientStatus("Follow-up", "Scheduled for follow-up appointment in one week"));
        }

        if (specializationrepo.count() == 0) {
            specializationrepo.save(new Specialization(
                    "Cardiology",
                    "Deals with disorders of the heart and the cardiovascular system.",
                    "https://images.pexels.com/photos/28856242/pexels-photo-28856242/free-photo-of-ng-i-ph-n-th-gian-tren-bai-bi-n-nhi-t-d-i-d-y-cat.jpeg"
            ));

            specializationrepo.save(new Specialization(
                    "Dermatology",
                    "Deals with the skin, hair, nails, and its diseases.",
                    "https://images.pexels.com/photos/19639386/pexels-photo-19639386/free-photo-of-bi-n-b-bi-n-kinh-ram-k-ngh.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            ));

            specializationrepo.save(new Specialization(
                    "Endocrinology",
                    "Deals with the endocrine system and its specific secretions called hormones.",
                    "https://images.pexels.com/photos/8760437/pexels-photo-8760437.jpeg"
            ));

            specializationrepo.save(new Specialization(
                    "Gastroenterology",
                    "Deals with the digestive system and its disorders.",
                    "https://images.pexels.com/photos/12590657/pexels-photo-12590657.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            ));

            specializationrepo.save(new Specialization(
                    "Hematology",
                    "Deals with blood and the blood-forming organs.",
                    "https://images.pexels.com/photos/8907219/pexels-photo-8907219.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            ));

        }

        if (clinicRepo.count() == 0) {
            Clinic clinic = Clinic.builder()
                    .name("Bệnh viện Đa khoa Quốc tế")
                    .address("Số 1, Đại Cồ Việt, Hai Bà Trưng, Hà Nội")
                    .phone("024 3974 3556")
                    .description("Bệnh viện Đa khoa Quốc tế là một trong những bệnh viện hàng đầu tại Hà Nội")
                    .image("https://phukhoaanviet.com/wp-content/uploads/anh-mong-to-10.jpg")
                    .build();
            clinicRepo.save(clinic);

            Clinic clinic1 = Clinic.builder()
                    .name("Bệnh viện Bạch Mai")
                    .address("Số 78, Đường Giải Phóng, Đồng Tâm, Hai Bà Trưng, Hà Nội")
                    .phone("024 3869 3736")
                    .description("Bệnh viện Bạch Mai là một trong những bệnh viện hàng đầu tại Hà Nội")
                    .image("https://phukhoaanviet.com/wp-content/uploads/anh-mong-to-50.jpg")
                    .build();
            clinicRepo.save(clinic1);

            Clinic clinic2 = Clinic.builder()
                    .name("Bệnh viện Việt Đức")
                    .address("Số 40, Tràng Tiền, Hoàn Kiếm, Hà Nội")
                    .description("Bệnh viện Việt Đức là một trong những bệnh viện hàng đầu tại Hà Nội")
                    .image("https://anhgaixinh.vn/wp-content/uploads/2023/02/gai-mac-do-gym-1-1.jpg")
                    .phone("024 3826 4135")
                    .build();
            clinicRepo.save(clinic2);

            Clinic clinic3 = Clinic.builder()
                    .name("Bệnh viện E")
                    .address("Số 87, Đường Giải Phóng, Đồng Tâm, Hai Bà Trưng, Hà Nội")
                    .description("Bệnh viện E là một trong những bệnh viện hàng đầu tại Hà Nội")
                    .image("https://gaixinhbikini.com/wp-content/uploads/2023/03/body-gai-tap-gym.jpg")
                    .phone("024 3869 3736")
                    .build();
            clinicRepo.save(clinic3);
        }

        if (userRepo.count() == 0 && roleRepo.count() > 0) {

            // tạo user cho admin có mọi quyền
            UserCreationDTO adminDTO = UserCreationDTO.builder()
                    .fullName("Nguyễn Trung Hiếu")
                    .description("Admin")
                    .username("godOfJava@999")
                    .password("godOfJava@999")
                    .email("godOfJava@gmail.com")
                    .address("Trái Đất")
                    .phone("0773307333")
                    .gender(Gender.MALE)
                    .avatar("https://photo.znews.vn/w1920/Uploaded/rotnba/2024_03_03/Snapinsta.app_431047292_18331084978106327_9041942873238468114_n_1080.jpg")
                    .status(Status.ACTIVE)
                    .roleTypes(Arrays.asList(
                            EnumRole.ADMIN, EnumRole.DOCTOR,
                            EnumRole.PATIENT, EnumRole.RECEPTIONIST
                    ))
                    .doctorInfo(DoctorInfoDTO.builder()
                            .specializationId(1L)
                            .clinicId(1L)
                            .achievements("Đã có nhiều năm kinh nghiệm")
                            .medicalTraining("Đại học Y Hà Nội")
                            .build())
                    .createPatient(true)
                    .build();
            dataSetupService.createUserWithRoles(adminDTO);

            // tạo lễ tân
            UserCreationDTO receptionist_1 = UserCreationDTO.builder()
                    .fullName("Phạm Văn C")
                    .description("Receptionist")
                    .username("receptionistC@999")
                    .password("receptionistC@999")
                    .email("receptionist_c@gmail.com")
                    .address("Đà Nẵng")
                    .phone("3333333333")
                    .gender(Gender.MALE)
                    .avatar("https://example.com/avatar3.jpg")
                    .status(Status.ACTIVE)
                    .roleTypes(Arrays.asList(
                            EnumRole.RECEPTIONIST,
                            EnumRole.PATIENT
                    ))
                    .createPatient(true)
                    .build();
            dataSetupService.createUserWithRoles(receptionist_1);

            // tạo user cho doctor có quyền doctor và patient
            List<UserCreationDTO> doctors = Arrays.asList(
                    UserCreationDTO.builder()
                            .fullName("Nguyễn Văn A")
                            .description("Doctor")
                            .username("doctorA@999")
                            .password("doctorA@999")
                            .email("doctor_a@gmail.com")
                            .address("Hà Nội")
                            .phone("1111111111")
                            .gender(Gender.MALE)
                            .avatar("https://example.com/avatarA.jpg")
                            .status(Status.ACTIVE)
                            .roleTypes(Arrays.asList(EnumRole.DOCTOR, EnumRole.PATIENT))
                            .doctorInfo(DoctorInfoDTO.builder()
                                    .specializationId(1L)
                                    .clinicId(1L)
                                    .achievements("Đã có 5 năm kinh nghiệm điều trị nội khoa")
                                    .medicalTraining("Đại học Y Hà Nội")
                                    .build())
                            .createPatient(true)
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Trần Thị B")
                            .description("Doctor")
                            .username("doctorB@888")
                            .password("doctorB@888")
                            .email("doctor_b@gmail.com")
                            .address("TP. Hồ Chí Minh")
                            .phone("2222222222")
                            .gender(Gender.FEMALE)
                            .avatar("https://example.com/avatarB.jpg")
                            .status(Status.ACTIVE)
                            .roleTypes(Arrays.asList(EnumRole.DOCTOR, EnumRole.PATIENT))
                            .doctorInfo(DoctorInfoDTO.builder()
                                    .specializationId(2L)
                                    .clinicId(2L)
                                    .achievements("Chuyên gia 7 năm trong lĩnh vực ngoại khoa")
                                    .medicalTraining("Đại học Y Dược TP. Hồ Chí Minh")
                                    .build())
                            .createPatient(true)
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Lê Văn C")
                            .description("Doctor")
                            .username("doctorC@777")
                            .password("doctorC@777")
                            .email("doctor_c@gmail.com")
                            .address("Đà Nẵng")
                            .phone("3333333333")
                            .gender(Gender.MALE)
                            .avatar("https://example.com/avatarC.jpg")
                            .status(Status.ACTIVE)
                            .roleTypes(Arrays.asList(EnumRole.DOCTOR, EnumRole.PATIENT))
                            .doctorInfo(DoctorInfoDTO.builder()
                                    .specializationId(1L)
                                    .clinicId(3L)
                                    .achievements("Chuyên gia điều trị các bệnh về tim mạch")
                                    .medicalTraining("Đại học Y Đà Nẵng")
                                    .build())
                            .createPatient(true)
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Phạm Thị D")
                            .description("Doctor")
                            .username("doctorD@666")
                            .password("doctorD@666")
                            .email("doctor_d@gmail.com")
                            .address("Cần Thơ")
                            .phone("4444444444")
                            .gender(Gender.FEMALE)
                            .avatar("https://example.com/avatarD.jpg")
                            .status(Status.ACTIVE)
                            .roleTypes(Arrays.asList(EnumRole.DOCTOR, EnumRole.PATIENT))
                            .doctorInfo(DoctorInfoDTO.builder()
                                    .specializationId(2L)
                                    .clinicId(4L)
                                    .achievements("Hơn 10 năm kinh nghiệm điều trị bệnh nhân")
                                    .medicalTraining("Đại học Y Cần Thơ")
                                    .build())
                            .createPatient(true)
                            .build()
            );

            doctors.forEach(dataSetupService::createUserWithRoles);

            // tạo user cho patient
            List<UserCreationDTO> patients = Arrays.asList(
                    UserCreationDTO.builder()
                            .fullName("Lê Văn E")
                            .description("Patient")
                            .username("patientE@999")
                            .password("patientE@999")
                            .email("patient_e@gmail.com")
                            .address("Cần Thơ")
                            .phone("5555555555")
                            .gender(Gender.MALE)
                            .avatar("https://example.com/avatar5.jpg")
                            .status(Status.ACTIVE)
                            .createPatient(true)
                            .roleTypes(Collections.singletonList(EnumRole.PATIENT))
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Trần Thị F")
                            .description("Patient")
                            .username("patientF@888")
                            .password("patientF@888")
                            .email("patient_f@gmail.com")
                            .address("Hà Nội")
                            .phone("6666666666")
                            .gender(Gender.FEMALE)
                            .avatar("https://example.com/avatar6.jpg")
                            .status(Status.ACTIVE)
                            .createPatient(true)
                            .roleTypes(Collections.singletonList(EnumRole.PATIENT))
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Nguyễn Văn G")
                            .description("Patient")
                            .username("patientG@777")
                            .password("patientG@777")
                            .email("patient_g@gmail.com")
                            .address("TP. Hồ Chí Minh")
                            .phone("7777777777")
                            .gender(Gender.MALE)
                            .avatar("https://example.com/avatar7.jpg")
                            .status(Status.ACTIVE)
                            .createPatient(true)
                            .roleTypes(Collections.singletonList(EnumRole.PATIENT))
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Phạm Thị H")
                            .description("Patient")
                            .username("patientH@666")
                            .password("patientH@666")
                            .email("patient_h@gmail.com")
                            .address("Đà Nẵng")
                            .phone("8888888888")
                            .gender(Gender.FEMALE)
                            .avatar("https://example.com/avatar8.jpg")
                            .status(Status.ACTIVE)
                            .createPatient(true)
                            .roleTypes(Collections.singletonList(EnumRole.PATIENT))
                            .build(),
                    UserCreationDTO.builder()
                            .fullName("Hoàng Văn I")
                            .description("Patient")
                            .username("patientI@555")
                            .password("patientI@555")
                            .email("patient_i@gmail.com")
                            .address("Hải Phòng")
                            .phone("9999999999")
                            .gender(Gender.MALE)
                            .createPatient(true)
                            .avatar("https://example.com/avatar9.jpg")
                            .status(Status.ACTIVE)
                            .roleTypes(Collections.singletonList(EnumRole.PATIENT))
                            .build()
            );

            // Lưu danh sách bệnh nhân
            patients.forEach(dataSetupService::createUserWithRoles);


        }

        if (consultationRepo.count() == 0) {
            // tạo user cho doctor
            Consultation handHygieneConsultation = Consultation.builder()
                    .question("What are the best practices for hand hygiene?")
                    .answer("To maintain proper hand hygiene, follow these steps:" +
                            "1. Wet your hands with clean, running water (warm or cold), turn off the tap, and apply soap." +
                            "2. Lather your hands by rubbing them together with the soap. Lather the backs of your hands, between your fingers, and under your nails.\n" +
                            "3. Scrub your hands for at least 20 seconds." +
                            "4. Rinse your hands well under clean, running water." +
                            "5. Dry your hands using a clean towel or air dry them.")
                    .build();
            consultationRepo.save(handHygieneConsultation);

            Consultation dangerousIllnessSymptoms = Consultation.builder()
                    .question("What are the common symptoms of dangerous illnesses?")
                    .answer("Some common symptoms of dangerous illnesses include:" +
                            "- Severe and persistent fever" +
                            "- Difficulty breathing or shortness of breath" +
                            "- Severe and persistent cough" +
                            "- Severe abdominal pain" +
                            "- Unexplained bruising or bleeding" +
                            "- Sudden onset of confusion or disorientatio" +
                            "- Severe and persistent headache" +
                            "If you experience any of these symptoms, seek medical attention immediately.")
                    .build();
            consultationRepo.save(dangerousIllnessSymptoms);

            Consultation temporaryBleedingControl = Consultation.builder()
                    .question("What are the steps to temporarily control bleeding?")
                    .answer("To temporarily control bleeding, follow these steps:" +
                            "1. Apply direct pressure to the wound using a clean cloth, towel, or piece of clothing." +
                            "2. Elevate the injured body part above the level of the heart, if possible." +
                            "3. If the bleeding does not stop with direct pressure, apply a tourniquet to the injured limb, above the wound." +
                            "4. Call emergency services or seek medical attention immediately.")
                    .build();
            consultationRepo.save(temporaryBleedingControl);

            Consultation foreignObjectRemoval = Consultation.builder()
                    .question("How should you handle a foreign object in the body?")
                    .answer("If someone has a foreign object lodged in their body, follow these steps:" +
                            "1. Do not attempt to remove the object, as this could cause further injury." +
                            "2. Stabilize the object by placing bulky dressings around it to keep it from moving." +
                            "3. Seek immediate medical attention." +
                            "4. If the object is in the eye, gently flush the eye with clean water or saline solution.")
                    .build();
            consultationRepo.save(foreignObjectRemoval);

            Consultation electricalShockFirstAid = Consultation.builder()
                    .question("What should you do if someone has suffered an electrical shock?")
                    .answer("If someone has suffered an electrical shock, follow these steps:" +
                            "1. Ensure the scene is safe and turn off the power source, if possible." +
                            "2. Call emergency services immediately." +
                            "3. If the person is unconscious, begin CPR if needed." +
                            "4. If the person is conscious, check for burns and treat them accordingly." +
                            "5. Monitor the person's breathing and circulation until emergency responders arrive.")
                    .build();

            consultationRepo.save(electricalShockFirstAid);
        }

        if (medicationRepo.count() == 0) {
            Medication Paracetamol = Medication.builder()
                    .name("Paracetamol") // tên thuốc
                    .dosage("500mg")     // liều lượng
                    .instructions("Take 1 tablet every 4-6 hours as needed for pain or fever.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Pain reliever") // loại thuốc
                    .build();

            Medication Ibuprofen = Medication.builder()
                    .name("Ibuprofen") // tên thuốc
                    .dosage("200mg")     // liều lượng
                    .instructions("Take 1 tablet every 4-6 hours as needed for pain or fever.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Pain reliever") // loại thuốc
                    .build();

            Medication Amoxicillin = Medication.builder()
                    .name("Amoxicillin") // tên thuốc
                    .dosage("500mg")     // liều lượng
                    .instructions("Take 1 tablet every 8 hours for 7-10 days.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antibiotic") // loại thuốc
                    .build();

            Medication Prednisone = Medication.builder()
                    .name("Prednisone") // tên thuốc
                    .dosage("5mg")     // liều lượng
                    .instructions("Take 1 tablet every morning for 5 days, then 1 tablet every other morning for 5 days.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Corticosteroid") // loại thuốc
                    .build();

            Medication Diphenhydramine = Medication.builder()
                    .name("Diphenhydramine") // tên thuốc
                    .dosage("25mg")     // liều lượng
                    .instructions("Take 1 tablet at bedtime as needed for sleep or allergy symptoms.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antihistamine") // loại thuốc
                    .build();

            Medication Aspirin = Medication.builder()
                    .name("Aspirin") // tên thuốc
                    .dosage("81mg")     // liều lượng
                    .instructions("Take 1 tablet every morning for heart health.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Pain reliever") // loại thuốc
                    .build();

            Medication Benadryl = Medication.builder()
                    .name("Benadryl") // tên thuốc
                    .dosage("25mg")     // liều lượng
                    .instructions("Take 1 tablet every 4-6 hours as needed for allergy symptoms.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antihistamine") // loại thuốc
                    .build();

            Medication Zyrtec = Medication.builder()
                    .name("Zyrtec") // tên thuốc
                    .dosage("10mg")     // liều lượng
                    .instructions("Take 1 tablet every morning for allergy symptoms.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antihistamine") // loại thuốc
                    .build();

            Medication Claritin = Medication.builder()
                    .name("Claritin") // tên thuốc
                    .dosage("10mg")     // liều lượng
                    .instructions("Take 1 tablet every morning for allergy symptoms.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antihistamine") // loại thuốc
                    .build();

            Medication Flonase = Medication.builder()
                    .name("Flonase") // tên thuốc
                    .dosage("50mcg")     // liều lượng
                    .instructions("Spray 2 sprays in each nostril every morning for allergy symptoms.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Nasal spray") // loại thuốc
                    .build();

            Medication Mucinex = Medication.builder()
                    .name("Mucinex") // tên thuốc
                    .dosage("600mg")     // liều lượng
                    .instructions("Take 1 tablet every 12 hours for congestion.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Expectorant") // loại thuốc
                    .build();

            Medication Robitussin = Medication.builder()
                    .name("Robitussin") // tên thuốc
                    .dosage("10mg")     // liều lượng
                    .instructions("Take 1-2 teaspoons every 4-6 hours for cough.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Cough syrup") // loại thuốc
                    .build();

            Medication Tylenol = Medication.builder()
                    .name("Tylenol") // tên thuốc
                    .dosage("500mg")     // liều lượng
                    .instructions("Take 1 tablet every 4-6 hours as needed for pain or fever.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Pain reliever") // loại thuốc
                    .build();

            Medication Sudafed = Medication.builder()
                    .name("Sudafed") // tên thuốc
                    .dosage("30mg")     // liều lượng
                    .instructions("Take 1 tablet every 4-6 hours for congestion.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Decongestant") // loại thuốc
                    .build();

            Medication PeptoBismol = Medication.builder()
                    .name("Pepto-Bismol") // tên thuốc
                    .dosage("262mg")     // liều lượng
                    .instructions("Take 2 tablets every 4-6 hours as needed for upset stomach or diarrhea.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antidiarrheal") // loại thuốc
                    .build();

            Medication Imodium = Medication.builder()
                    .name("Imodium") // tên thuốc
                    .dosage("2mg")     // liều lượng
                    .instructions("Take 2 tablets after the first loose stool, then 1 tablet after each subsequent loose stool, up to 4 tablets per day.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antidiarrheal") // loại thuốc
                    .build();

            Medication Zantac = Medication.builder()
                    .name("Zantac") // tên thuốc
                    .dosage("150mg")     // liều lượng
                    .instructions("Take 1 tablet every 12 hours for heartburn or acid indigestion.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antacid") // loại thuốc
                    .build();

            Medication Prilosec = Medication.builder()
                    .name("Prilosec") // tên thuốc
                    .dosage("20mg")     // liều lượng
                    .instructions("Take 1 tablet every morning for heartburn or acid indigestion.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antacid") // loại thuốc
                    .build();

            Medication Tums = Medication.builder()
                    .name("Tums") // tên thuốc
                    .dosage("500mg")     // liều lượng
                    .instructions("Take 2 tablets as needed for heartburn or acid indigestion.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antacid") // loại thuốc
                    .build();

            Medication GasX = Medication.builder()
                    .name("Gas-X") // tên thuốc
                    .dosage("125mg")     // liều lượng
                    .instructions("Take 1-2 tablets as needed for gas or bloating.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Antigas") // loại thuốc
                    .build();

            Medication Miralax = Medication.builder()
                    .name("Miralax") // tên thuốc
                    .dosage("17g")     // liều lượng
                    .instructions("Mix 1 capful in 8 ounces of water and drink once daily for constipation.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Laxative") // loại thuốc
                    .build();

            Medication Colace = Medication.builder()
                    .name("Colace") // tên thuốc
                    .dosage("100mg")     // liều lượng
                    .instructions("Take 1-2 tablets as needed for constipation.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Stool softener") // loại thuốc
                    .build();

            Medication Dulcolax = Medication.builder()
                    .name("Dulcolax") // tên thuốc
                    .dosage("5mg")     // liều lượng
                    .instructions("Take 1-2 tablets as needed for constipation.") // hướng dẫn sử dụng
                    .manufacturer("Generic") // nhà sản xuất
                    .type("Laxative") // loại thuốc
                    .build();

            medicationRepo.save(Paracetamol);
            medicationRepo.save(Ibuprofen);
            medicationRepo.save(Amoxicillin);
            medicationRepo.save(Prednisone);
            medicationRepo.save(Diphenhydramine);
            medicationRepo.save(Aspirin);
            medicationRepo.save(Benadryl);
            medicationRepo.save(Zyrtec);
            medicationRepo.save(Claritin);
            medicationRepo.save(Flonase);
            medicationRepo.save(Mucinex);
            medicationRepo.save(Robitussin);
            medicationRepo.save(Tylenol);
            medicationRepo.save(Sudafed);
            medicationRepo.save(PeptoBismol);
            medicationRepo.save(Imodium);
            medicationRepo.save(Zantac);
            medicationRepo.save(Prilosec);
            medicationRepo.save(Tums);
            medicationRepo.save(GasX);
            medicationRepo.save(Miralax);
            medicationRepo.save(Colace);
            medicationRepo.save(Dulcolax);


        }


    }
}
