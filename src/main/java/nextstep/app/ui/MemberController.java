package nextstep.app.ui;

import nextstep.app.domain.Member;
import nextstep.app.domain.MemberRepository;
import nextstep.app.security.Authentication;
import nextstep.app.security.BasicAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> list(HttpServletRequest request) {
        Authentication basicAuthentication = BasicAuthentication.from(request.getHeader(AUTHORIZATION));
        Member member = memberRepository.findByEmail(basicAuthentication.getPrincipal())
                .orElseThrow(AuthenticationException::new);
        if (!member.matchPassword(basicAuthentication.getCredentials())) {
            throw new AuthenticationException();
        }

        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }

}
