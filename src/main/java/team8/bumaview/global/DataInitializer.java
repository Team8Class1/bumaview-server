package team8.bumaview.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team8.bumaview.domain.answer.domain.Answer;
import team8.bumaview.domain.answer.persistence.AnswerRepository;
import team8.bumaview.domain.category.domain.Category;
import team8.bumaview.domain.category.persistence.CategoryRepository;
import team8.bumaview.domain.company.domain.Company;
import team8.bumaview.domain.company.persistence.CompanyRepository;
import team8.bumaview.domain.favorite.domain.Favorite;
import team8.bumaview.domain.favorite.persistence.FavoriteRepository;
import team8.bumaview.domain.group.domain.Group;
import team8.bumaview.domain.group.persistence.GroupRepository;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.interview.persistence.InterviewRepository;
import team8.bumaview.domain.interviewcategory.domain.InterviewCategory;
import team8.bumaview.domain.interviewcategory.persistence.InterviewCategoryRepository;
import team8.bumaview.domain.interviewgroup.domain.InterviewGroup;
import team8.bumaview.domain.interviewgroup.persistence.InterviewGroupRepository;
import team8.bumaview.domain.user.domain.Role;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final CategoryRepository categoryRepository;
    private final InterviewCategoryRepository interviewCategoryRepository;
    private final AnswerRepository answerRepository;
    private final GroupRepository groupRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        // 1. 회사 생성
        String[] companyNames = {"마이다스IT", "신한은행", "블루바이저", "아이디노", "니더", "라이너", "핀다", "브랜치앤바운드", "아키스케치", "샌드버그", "후아", "공감오래컨텐츠", "쏘카", "씨메스", "똑개", "더스팟", "지오영", "라포랩스", "서플라이스", "잉카인터넷", "미르니", "드래프타입", "달파", "사이버다임", "U2SR", "우리웍스", "큐오티", "바카티오", "리얼시큐", "썬컴"};
        String[] companyLinks = {"http://www.midasit.com/",
                "https://www.shinhan.com/index.jsp",
                "https://www.highbuff.com/",
                "http://www.idino.co.kr/",
                "http://gubgoo.com",
                "https://liner.com/ko/careers",
                "http://www.finda.co.kr",
                "https://www.hankyung.com/article/202504305663i",
                "https://www.archisketch.com/ko/blog/6784a5a07860420012d4a235",
                "https://bluepoint.ac/portfolio/1433",
                "https://hooaah.cafe24.com/",
                "https://gonggamore.com/",
                "https://www.socarcorp.kr/",
                "https://cmesrobotics.ai",
                "https://www.toktokhan.dev/",
                "www.thespot.kr",
                "https://www.geo-young.com/",
                "https://www.rapportlabs.kr/",
                "https://ofsupplies.com/",
                "http://www.inca.co.kr/",
                "www.mirny.io",
                "https://www.draftype.work/login",
                "https://dalpha.so/company",
                "www.cyberdigm.co.kr",
                "http://u2sr.webadsky.net/bbs/board.php?bo_table=notice&wr_id=30&sst=wr_datetime&sod=desc&sop=and&page=1",
                "https://www.uriworks.com/",
                "http://www.qot.co.kr/wordpress/?page_id=1680",
                "https://www.vacatio.us/",
                "http://www.realsecu.net",
                "http://www.suncom.co.kr"};

        log.info("companyName.length : {}, companyLink.length : {}", companyNames.length, companyLinks.length);

        for(int i = 0; i <  companyNames.length; i++) {
            Company company = Company.builder()
                    .name(companyNames[i])
                    .link(companyLinks[i])
                    .build();
            companyRepository.save(company);
        }


        // 2. 유저 생성
        User user = User.builder()
                .userId("user1")
                .password("password1")
                .role(Role.BASIC)
                .birthday(sdf.parse("2025-09-10"))
                .build();
        userRepository.save(user);

        // 3. 인터뷰 생성
        Company company = companyRepository.findById(1L).orElse(null);
        Interview interview = Interview.builder()
                .question("solid?")
                .questionAt(sdf.parse("2025-09-10"))
                .company(company)
                .build();
        interviewRepository.save(interview);
        String[] categoryNames = {"back", "front", "infra", "security", "bank", "design", "ai", "embedded", "game"};
        for(String categoryName : categoryNames) {
            Category category = Category.builder()
                    .name(categoryName)
                    .build();
            categoryRepository.save(category);
        }

        Category category = categoryRepository.findById(1L).orElse(null);
        InterviewCategory interviewCategory = InterviewCategory.builder()
                .interview(interview)
                .category(category)
                .build();
        interviewCategoryRepository.save(interviewCategory);

        Answer answer1 = Answer.builder()
                .answer("answer1")
                .isPrivate(true)
                .likeCount(3)
                .user(user)
                .interview(interview)
                .build();
        answerRepository.save(answer1);

        Answer reply1 = Answer.builder()
                .answer("answer1 reply")
                .isPrivate(false)
                .likeCount(0)
                .user(user)
                .interview(interview)
                .parent(answer1)
                .build();
        answerRepository.save(reply1);

        Answer reply2 = Answer.builder()
                .answer("answer1 reply22")
                .isPrivate(false)
                .likeCount(0)
                .user(user)
                .interview(interview)
                .parent(answer1)
                .build();
        answerRepository.save(reply2);

        Answer answer2 = Answer.builder()
                .answer("answer2")
                .isPrivate(false)
                .likeCount(3)
                .user(user)
                .interview(interview)
                .build();
        answerRepository.save(answer2);

        Group group = Group.builder()
                .user(user)
                .name("부소마고 4기")
                .build();
        groupRepository.save(group);

        String[] favoriteList = {"back", "front", "infra", "security", "bank", "design", "ai", "embedded", "game"};
        for(String favoriteName : favoriteList) {
            Favorite favorite = Favorite.builder()
                    .name(favoriteName)
                    .build();
            favoriteRepository.save(favorite);
        }
        System.out.println("초기 데이터 삽입 완료!");
    }
}
