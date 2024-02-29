package com.uni.mental.member.Controller;

import com.uni.mental.member.model.dao.MemberDao;
import com.uni.mental.member.model.dto.MemberDto;
import com.uni.mental.member.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("login")
public class MemberController {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;

    @GetMapping("/loginpage")
    public void memberLoginForm(){}

    @GetMapping("/agreement")
    public String agreement(HttpSession session) {
        // Check if the user has already logged in via Kakao
        if (session.getAttribute("email") != null && session.getAttribute("nickname") != null) {
            // If logged in, redirect to additional info page
            return "redirect:/login/additional-info";
        } else {
            // If not logged in, show the agreement page
            return "login/agreement";
        }
    }
    @GetMapping("/enroll")
    public String enrollForm() {
        return "login/enroll";
    }

    @PostMapping("/enroll")
    public String enrollMember(@RequestParam("birthYear") String birthYear,
                               @RequestParam("birthMonth") String birthMonth,
                               @RequestParam("birthDate") String birthDate,
                               @RequestParam("post") String post,
                               @RequestParam("address1") String address1,
                               @RequestParam("address2") String address2,
                               MemberDto memberDto,
                               Model model) {
        // 주소 처리
        String fullAddress = post + "/" + address1 + "/" + address2;
        memberDto.setAdd(fullAddress);

        // 생일 타입 처리
        String birthString = birthYear + "-" + birthMonth + "-" + birthDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthString, formatter);
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthday);
        memberDto.setBir(sqlDate);

        String encodedPassword = passwordEncoder.encode(memberDto.getPwd());
        memberDto.setPwd(encodedPassword);

        // 데이터 베이스에 저장
        memberDao.enrollMember(memberDto);

        model.addAttribute("memberDto", memberDto);

        System.out.println("memberDto: " + memberDto);

        return "redirect:/login/success";
    }

    @ResponseBody
    @RequestMapping("idCheck")
    public String idCheck(String id) {

        int count = memberService.idCheck(id);

        return String.valueOf(count);
    }

    @ResponseBody
    @RequestMapping("nicknameCheck")
    public String nicknameCheck(String nick) {

        int count = memberService.nicknameCheck(nick);

        return String.valueOf(count);
    }
    @GetMapping("/success")
    public String enrollsuccess() {return "login/success";}

    // 카카오 로그인
    @PostMapping("/kakao-login")
    public String kakaoLogin(@RequestParam("email") String email,
                             @RequestParam("nickname") String nickname,
                             HttpSession session) {
        // 데이터베이스에서 이메일 확인
        MemberDto existingMember = memberDao.findByEmail(email);
        if (existingMember != null) {
            // 이메일이 이미 존재하는 경우, 세션에 사용자 정보 저장
            session.setAttribute("id", existingMember.getId());
            session.setAttribute("email", existingMember.getEmail());
            session.setAttribute("nickname", existingMember.getNick());

            // 로그 추가: 세션에 저장된 사용자 정보 확인
            System.out.println("세션에 저장된 아이디: " + session.getAttribute("id"));
            System.out.println("세션에 저장된 이메일: " + session.getAttribute("email"));
            System.out.println("세션에 저장된 닉네임: " + session.getAttribute("nickname"));

            // 사용자 권한 설정 (예: ROLE_USER)
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));

            // Authentication 객체 생성. 여기서 principal에는 사용자의 ID(이메일)를 사용합니다.
            Authentication auth = new UsernamePasswordAuthenticationToken(existingMember.getId(), null, authorities);

            // SecurityContext에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(auth);

            // 메인 페이지로 리다이렉트
            return "redirect:/";
        } else {
            // 새 사용자 정보를 데이터베이스에 등록
            MemberDto newMember = new MemberDto();
            newMember.setId(email);
            newMember.setEmail(email);
            newMember.setNick(nickname);
            memberDao.enrollMember(newMember);

            // 세션 설정
            session.setAttribute("id", email);
            session.setAttribute("email", email);
            session.setAttribute("nickname", nickname);

            // 로그 추가: 새로운 사용자 정보가 세션에 저장되었는지 확인
            System.out.println("새로운 사용자의 아이디가 세션에 저장됨: " + session.getAttribute("id"));
            System.out.println("새로운 사용자의 이메일이 세션에 저장됨: " + session.getAttribute("email"));
            System.out.println("새로운 사용자의 닉네임이 세션에 저장됨: " + session.getAttribute("nickname"));

            // 동의 페이지로 리다이렉트
            return "redirect:/login/agreement";
        }
    }





    // 추가 정보 입력 페이지 요청 처리
    @GetMapping("/additional-info")
    public String additionalInfoForm(HttpSession session, Model model) {
        System.out.println("Session email: " + session.getAttribute("email")); // 로그 추가
        System.out.println("Session nickname: " + session.getAttribute("nickname")); // 로그 추가

        if (session.getAttribute("email") == null || session.getAttribute("nickname") == null) {
            // 세션에 이메일 또는 닉네임이 없으면 로그인 페이지로 리디렉션
            return "redirect:/login/loginpage";
        }

        // 추가 정보를 입력받는 페이지로 모델에 이메일과 닉네임 값을 설정하여 전달
        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("nickname", session.getAttribute("nickname"));


        return "login/additional-info";
    }


    @PostMapping("/submit-additional-info")
    public String submitAdditionalInfo(@ModelAttribute MemberDto memberDto, HttpSession session) {
        String email = (String) session.getAttribute("email");
        String nickname = (String) session.getAttribute("nickname");
        // 사용자로부터 받은 비밀번호를 BCrypt 형식으로 인코딩
        String encodedPassword = passwordEncoder.encode(memberDto.getPwd());
        // email 값을 MEMBER_ID로 사용
        memberDto.setId(email);
        memberDto.setEmail(email);
        memberDto.setNick(nickname);
        memberDto.setPwd(encodedPassword);

        System.out.println("Before saving to database: " + memberDto); // 로그 추가
        memberDao.enrollMember(memberDto);
        System.out.println("After saving to database: " + memberDto); // 로그 추가

        session.removeAttribute("email");
        session.removeAttribute("nickname");

        return "redirect:/login/success";
    }


//    // 구글 로그인
//    @PostMapping("/google-login")
//    public String googleLogin(@RequestParam("email") String email,
//                              @RequestParam("nickname") String nickname,
//                              Model model) {
//        return processSocialLogin(email, nickname, model);
//    }
//
//    // 네이버 로그인
//    @PostMapping("/naver-login")
//    public String naverLogin(@RequestParam("email") String email,
//                             @RequestParam("nickname") String nickname,
//                             Model model) {
//        return processSocialLogin(email, nickname, model);
//    }
//
//    // 소셜로그인 디비에 저장
//    private String processSocialLogin(String email, String nickname, Model model) {
//        MemberDto memberDto = new MemberDto();
//        memberDto.setId(email);
//        memberDto.setNick(nickname);
//
//        memberDao.enrollMember(memberDto);
//
//        model.addAttribute("memberDto", memberDto);
//
//        return "redirect:/login/success";
//    }
}
