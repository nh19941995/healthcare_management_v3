package org.example.healthcare_management_v2.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.healthcare_management_v2.entities.*;

import org.example.healthcare_management_v2.enums.EnumRole;
import org.example.healthcare_management_v2.enums.Gender;
import org.example.healthcare_management_v2.enums.Status;
import org.example.healthcare_management_v2.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
    private final PasswordEncoder passwordEncoder;
    private final ConsultationRepo consultationRepo;
    private final MedicationRepo medicationRepo;

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
                    .address("Số 40, Tràng Tiền, Ho��n Kiếm, Hà Nội")
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
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepo.findByName(
                    EnumRole.ADMIN.getRoleName()
            ).orElseThrow(() -> new EntityNotFoundException("Role not found"));

            Role doctorRole = roleRepo.findByName(
                    EnumRole.DOCTOR.getRoleName()
            ).orElseThrow(() -> new EntityNotFoundException("Role not found"));

            Role patientRole = roleRepo.findByName(
                    EnumRole.PATIENT.getRoleName()
            ).orElseThrow(() -> new EntityNotFoundException("Role not found"));

            roles.add(adminRole);
            roles.add(doctorRole);
            roles.add(patientRole);

            // Admin
            User admin = User.builder()
                    .fullName("Nguyễn Trung Hiếu")
                    .description("Admin")
                    .username("godOfJava@999")
                    .password(passwordEncoder.encode("godOfJava@999"))
                    .email("godOfJava@gmail.com")
                    .address("Trái Đất")
                    .phone("0773307333")
                    .gender(Gender.MALE)
                    .avatar("https://photo.znews.vn/w1920/Uploaded/rotnba/2024_03_03/Snapinsta.app_431047292_18331084978106327_9041942873238468114_n_1080.jpg")
                    .status(Status.ACTIVE)
                    .build();
            admin.setRoles(roles);
            userRepo.save(admin);

            // lấy ra user vừa tạo
            User userAdmin = userRepo.findByUsername("godOfJava@999")
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            // lấy ra status patient
            PatientStatus status_1 = patientStatusrepo.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("Patient status not found"));
            // tạo patient
            Patient patient_1 = Patient.builder()
                    .user(userAdmin)
                    .status(status_1)
                    .build();
            // Gán patient cho user
            userAdmin.setPatient(patient_1);
            // Lưu User, vì điều này sẽ tự động lưu cả Patient nhờ cascade (nếu cascade đã được thiết lập)
            userRepo.save(userAdmin);




            // patient
            User user = User.builder()
                    .fullName("Obama")
                    .username("ababab@A111")
                    .description("Patient")
                    .password(passwordEncoder.encode("ababab@A111"))
                    .email("abama@gmail.com")
                    .address("Trái Đất")
                    .phone("0273307333")
                    .avatar("https://images.pexels.com/photos/3622619/pexels-photo-3622619.jpeg")
                    .gender(Gender.MALE)
                    .status(Status.ACTIVE)
                    .build();
            Set<Role> rolesUser = new HashSet<>();
            rolesUser.add(patientRole);
            user.setRoles(rolesUser);
            userRepo.save(user);
            // lấy ra user vừa tạo
            User user1 = userRepo.findByUsername("ababab@A111")
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            // lấy ra status patient
            PatientStatus status = patientStatusrepo.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("Patient status not found"));
            // tạo patient
            Patient patient = Patient.builder()
                    .user(user1)
                    .status(status)
                    .build();
            // Gán patient cho user
            user1.setPatient(patient);
            // Lưu User, vì điều này sẽ tự động lưu cả Patient nhờ cascade (nếu cascade đã được thiết lập)
            userRepo.save(user1);



            // doctor
            Specialization specialization1 = specializationrepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Specialization not found"));
            Specialization specialization2 = specializationrepo.findById(2L).orElseThrow(() -> new EntityNotFoundException("Specialization not found"));

            Clinic clinic1 = clinicRepo.findById(1L).orElseThrow(() -> new EntityNotFoundException("Clinic not found"));
            Clinic clinic2 = clinicRepo.findById(2L).orElseThrow(() -> new EntityNotFoundException("Clinic not found"));


            // tạo user cho doctor
            User userDoctor = User.builder()
                    .fullName("John Doe")
                    .username("johndoe@A123")
                    .description("Visitor")
                    .password(passwordEncoder.encode("johndoe@A123"))
                    .email("johndoe@example.com")
                    .address("Mars")
                    .phone("1234567890")
                    .avatar("https://images.pexels.com/photos/123456/pexels-photo-123456.jpeg")
                    .gender(Gender.MALE)
                    .status(Status.ACTIVE)
                    .build();
            Set<Role> rolesUserDoctor = new HashSet<>();
            rolesUserDoctor.add(doctorRole);
            rolesUserDoctor.add(patientRole);
            userDoctor.setRoles(rolesUserDoctor);

            // tạo doctor
            Doctor doctor1 = new Doctor();
            doctor1.setUser(userDoctor);
            doctor1.setSpecialization(specialization1);
            doctor1.setClinic(clinic1);
            doctor1.setStatus(Status.ACTIVE);
            doctor1.setAchievements("Đã có nhiều năm kinh nghiệm");
            doctor1.setMedicalTraining("Đại học Y Hà Nội");
            // lưu doctor
            doctorRepo.save(doctor1);
            // lưu user
            userRepo.save(userDoctor);


            // tạo user cho doctor
            User userDoctor_2 = User.builder()
                    .fullName("Nguyễn Văn B")
                    .description("Doctor")
                    .username("doctorB@A111")
                    .password(passwordEncoder.encode("doctorB@A111"))
                    .email("doctorb@gmail.com")
                    .address("Trái Đất")
                    .phone("0213377333")
                    .avatar("https://images.pexels.com/photos/15793630/pexels-photo-15793630/free-photo-of-bi-n-th-i-trang-b-bi-n-ngay-l.jpeg")
                    .gender(Gender.MALE)
                    .status(Status.ACTIVE)
                    .build();
            userDoctor_2.setRoles(rolesUserDoctor);
            // tạo doctor
            Doctor doctor2 = new Doctor();
            doctor2.setUser(userDoctor_2);
            doctor2.setSpecialization(specialization1);
            doctor2.setClinic(clinic1);
            doctor2.setStatus(Status.ACTIVE);
            doctor2.setAchievements("Đã có nhiều năm kinh nghiệm");
            doctor2.setMedicalTraining("Đại học Y Đà Nẵng");
            // lưu doctor
            doctorRepo.save(doctor2);
            // lưu user
            userRepo.save(userDoctor_2);


            // tạo user cho doctor
            User userDoctor_3 = User.builder()
                    .fullName("Nguyễn Văn C")
                    .description("Doctor")
                    .username("doctorC@A111")
                    .password(passwordEncoder.encode("doctorC@A111"))
                    .email("doctorc@gmail.com")
                    .address("Trái Đất")
                    .avatar("https://images.pexels.com/photos/28663059/pexels-photo-28663059.jpeg")
                    .phone("0213377383")
                    .gender(Gender.MALE)
                    .status(Status.ACTIVE)
                    .build();
            userDoctor_3.setRoles(rolesUserDoctor);
            // tạo doctor
            Doctor doctor3 = new Doctor();
            doctor3.setUser(userDoctor_3);
            doctor3.setSpecialization(specialization1);
            doctor3.setClinic(clinic1);
            doctor3.setStatus(Status.ACTIVE);
            doctor3.setAchievements("Đã có nhiều năm kinh nghiệm");
            doctor3.setMedicalTraining("Đại học Y TP Hồ Chí Minh");
            // lưu doctor
            doctorRepo.save(doctor3);
            // lưu user
            userRepo.save(userDoctor_3);
            // set role cho user


        }

        if(consultationRepo.count() == 0) {
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

        if(medicationRepo.count() == 0){
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
