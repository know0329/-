package com.d208.giggybank.service;

import com.d208.giggybank.domain.BankAccount;
import com.d208.giggybank.domain.BankAccountHistory;
import com.d208.giggybank.domain.Customer;
import com.d208.giggybank.dto.*;
import com.d208.giggybank.repository.BankAccountHistoryRepository;
import com.d208.giggybank.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankService {


    private final BankAccountRepository bankAccountRepository;
    private final BankAccountHistoryRepository bankAccountHistoryRepository;


    @Transactional
    public void addTransactionService(UserTransactionInfoDto userTransactionInfoDto){
        String accountnumber = userTransactionInfoDto.getAccountNumber();
        Integer dwamount = userTransactionInfoDto.getDwamount();
        String transactionType = userTransactionInfoDto.getTransactionType();
        String content = userTransactionInfoDto.getContent();

        // 은행 계좌
        Optional<BankAccount> currentbankaccount = bankAccountRepository.findByAccountNumber(accountnumber);

        if (currentbankaccount.isPresent() && transactionType.equals("입금")){

            BankAccountHistory bankAccountHistory = BankAccountHistory.builder()
                    .amount(currentbankaccount.get().getBalance() + dwamount)
                    .transactionType(transactionType)
                    .deposit(dwamount)
                    .content(content)
                    .transactionDate(LocalDateTime.now())
                    .bankAccount(currentbankaccount.get())
                    .build();

            bankAccountHistoryRepository.save(bankAccountHistory);

            currentbankaccount.get().updateBalance(currentbankaccount.get().getBalance() + dwamount);
            bankAccountRepository.save(currentbankaccount.get());

        } else {
            BankAccountHistory bankAccountHistory = BankAccountHistory.builder()
                    .amount(currentbankaccount.get().getBalance() - dwamount)
                    .transactionType(transactionType)
                    .withdraw(dwamount)
                    .content(content)
                    .transactionDate(LocalDateTime.now())
                    .bankAccount(currentbankaccount.get())
                    .build();
            bankAccountHistoryRepository.save(bankAccountHistory);

            currentbankaccount.get().updateBalance(currentbankaccount.get().getBalance() - dwamount);
            bankAccountRepository.save(currentbankaccount.get());
        }
    }

    @Transactional
    public List<AccountHistoryDto> searchTransactionService(UserAccountNumberDto userAccountNumberDto){
        String useraccountnumber = userAccountNumberDto.getAccountNumber();
        // 사용자 계좌의 객체
        BankAccount currentbankaccount = bankAccountRepository.findByAccountNumber(useraccountnumber).orElse(null);
//        Long bankAccountId = currentbankaccount.get().getId();

//        Long bankAccountId = currentbankaccount.map(BankAccount::getId).orElse(null);

//        List<BankAccountHistory> userbankhistories = bankAccountHistoryRepository.findByBankAccountId(bankAccountId);
        //userbankhistory. 데이터들을 dto에 탁탁탁 넣어서  dto를 리턴

        LocalDateTime startDate = userAccountNumberDto.getStartDate();
        LocalDateTime endDate = userAccountNumberDto.getEndDate();
        List<BankAccountHistory> userbankhistories = bankAccountHistoryRepository.
                findByBankAccountAndTransactionDateTimeBetween(currentbankaccount, startDate, endDate);

        List<AccountHistoryDto> accountHistoryDtos = userbankhistories.stream()
                .map(history -> AccountHistoryDto.builder()
                        .amount(history.getAmount())
                        .transactionDate(history.getTransactionDate())
                        .transactionType(history.getTransactionType())
                        .deposit(history.getDeposit())
                        .withdraw(history.getWithdraw())
                        .content(history.getContent())
                        .build())
                .collect(Collectors.toList());

        return accountHistoryDtos;
    }


    // 계좌번호와 연결된 생일을 조회한 후 받은 값과 일치하면 +1원 후 리턴 아니면 null 리턴
    @Transactional
    public AuthResponseDto checkAuth(AuthDto authDto) {
        String accountNumber = authDto.getAccountNumber();
        String birthday = authDto.getBirthday();

        // 서버의 엔티티에서 해당 계좌번호 검색
//        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByAccountNumber(accountNumber);
        BankAccount bankAccountOptional = bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);

        System.out.println("aaaaaa = = ="+bankAccountOptional);

        if (bankAccountOptional == null) {
            return AuthResponseDto.builder().build();
        }
        else {
            // 계좌번호 있을 경우
//            int bankAccount = bankAccountOptional.get().getBalance();
//            Customer customer = bankAccountOptional.get().getCustomer();
            int bankAccount = bankAccountOptional.getBalance();
            Customer customer = bankAccountOptional.getCustomer();


            if (customer != null && customer.getBirthday().equals(birthday)) {
                // 일치하는 생일 있을 경우

                String randomContent = generateRandomContent();

                // 1원 이체(=1원추가)
                BankAccountHistory bankAccountHistory = BankAccountHistory.builder()
                        .amount(bankAccountOptional.getBalance() + 1)
                        .transactionType("입금")
                        .deposit(1)
                        .content(randomContent)
                        .transactionDate(LocalDateTime.now())
                        .bankAccount(bankAccountOptional)
                        .build();
                bankAccountHistoryRepository.save(bankAccountHistory);
                bankAccountOptional.updateBalance(bankAccountOptional.getBalance() + 1);
                bankAccountRepository.save(bankAccountOptional);

                return AuthResponseDto.builder()
                        .amount(1)
                        .deposit(1)
                        .transactionDate(LocalDateTime.now())
                        .transactionType("입금")
                        .content(randomContent)
                        .build();
            }
            else {
                // 빈 AuthResponseDto 반환
                return AuthResponseDto.builder().build();
            }


        }

    }

    private String generateRandomContent() {
        String[] words = {"지기뱅크", "쥐금통통", "생활수칙", "손발준배",
                "비트코인", "방방곡곡", "도원결의", "스파게티", "대한민국",
                "누네띠네", "어장관리", "버터구이", "업데이트", "카페베네",
                "붉은노을", "소녀시대", "스타워즈", "백설공주", "요구르트",
                "오토바이", "파인애플", "스타필드", "스타벅스", "사자성어",
                "비밀번호", "계좌번호", "우리나라", "생년월일", "고객센터",
                "알레르기", "현장학습", "뭉게구름", "호랑나비", "종이접기",
                "주의사항", "탄수화물", "삼각김밥", "비트박스", "소녀시대",
                "미끄럼틀", "원두커피", "하모니카", "신용카드", "타임오버",
                "하드캐리", "아카시아", "샌드위치", "롯데리아", "와이파이",
                "플라스틱", "맘스터치", "텔레비전", "물은셀프", "허리케인",
                "스테이크", "연지곤지", "카페라떼", "업데이트", "인어공주",
                "마요네즈", "스마트폰", "다이어트", "아이패드", "김치찌개",
                "오버워치", "오토바이", "파인애플", "사자성어", "비밀번호",
                "계좌번호", "우리나라", "생년월일", "고객센터", "알레르기",
                "현장학습", "뭉게구름", "호랑나비", "종이접기", "주의사항",
                "뉴발란스", "인생네컷", "도시어부", "브로콜리", "비타오백",
                "낄끼빠빠", "겨울왕국", "국민청원", "어린왕자", "안전제일",
                "바른생활", "고슴도치", "제품설명", "총각김치", "두루치기",
                "후라이팬", "카놀라유", "영웅호걸", "어벤져스", "모래시계",
                "비트코인", "방방곡곡", "도원결의", "스파게티", "대한민국",
                "피라미드", "일편단심", "손목시계", "공중전화", "다이어트",
                "고슴도치", "국어사전", "계좌번호", "롯데마트", "돼지고기",
                "포르투칼", "닭가슴살", "데스노트", "우루과이", "된장찌개",
                "포스트잇", "코카콜라", "영어학원", "동서남북", "홈플러스",
                "여자친구", "네덜란드", "남자친구", "파인애플", "모나리자",
                "리락쿠마", "고등학교", "태권브이", "계란말이", "초등학교",
                "신데렐라", "전통민요", "전라남도", "신사임당", "김치찌개",
                "동그랑땡", "어장관리", "버터구이", "황금어장", "카카오톡",
                "신혼여행", "박테리아", "페이스톡", "신혼여행", "허리케인",
                "양념치킨", "홈페이지", "다이어리", "바이올린", "트와이즈",
                "페이스북", "드래곤볼", "십중팔구", "타임머신", "쇠똥구리",
                "경상남도", "경상북도", "전라남도", "전라북도", "충청북도",
                "충청남도", "아이언맨", "나폴레옹", "자일리톨", "하이패스",
                "블랙핑크", "아디다스", "스파게티", "즐겨찾기", "일기예보",
                "공지사항", "헤드라인", "저승사자", "나무늘보", "삼각김밥",
                "다다익선", "설왕설래", "페브리즈", "인공지능", "무념무상",
                "웰시코기", "개과천선", "일기예보", "생활용품", "즐겨찾기",
                "어린이날", "동문서답", "국가대표", "햇님달님", "국민연금",
                "삼각김밥", "직사광선", "크레파스", "프로그램", "국회의원",
                "직권남용", "그린벨트", "블루벨트", "아기용품", "고속도로",
                "오토바이", "비밀번호", "카사노바", "아프리카", "고슴도치",
                "갈팡질팡", "주의사항", "멜로디언", "세숫비누", "양념치킨",
                "간장치킨", "파닭치킨", "골드미스", "핸드크림", "금상첨화",
                "호랑나비", "비타오백", "고객센터", "생년월일", "보노보노",
                "사용방법", "두루치기", "후라이팬", "밀폐용기", "과유불급",
                "해바라기", "학생회장", "배추김치", "장화홍련", "예방주사",
                "금시초문", "속도위반", "성형외과", "알레르기", "다다익선",
                "유통기한", "뭉게구름", "카렐레온", "종이접기", "유니클로",
                "이용약관", "신혼여행", "칠순잔치", "영어사전", "중고나라",
                "불가리아", "닭볶음탕", "손준배발", "산들바람", "바다바람",
                "유노윤호", "믹키유천", "영웅재중", "시아준수", "최강창민",
                "발레파킹", "허수아비", "강강술래", "천지창조", "안전운전",
                "나무늘보", "퀵서비스", "직사광선", "드래곤볼", "십중팔구",
                "고진감래", "두부김치", "타임머신", "생로병사", "쇠똥구리",
                "블랙핑크", "저승사자", "모차르트", "바이올린", "사주팔자",
                "어린이집", "술래잡기", "오버워치", "젝스키스", "마요네즈",
                "송이버섯", "롯데리아", "바른생활", "헬리콥터", "설왕설래",
                "된장찌개", "프로젝트", "바디로션", "백과사전", "알람시계",
                "올리브영", "선글라스", "스승의날", "치과의사", "쓰레기통",
                "카페베네", "붉은노을", "요구르트", "청소도구", "브라우니",
                "삼시세끼", "코카콜라", "흔들의자", "삼성전자", "신서유기"
        };

        // 랜덤 문자열 (4글자)
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        String randomWord = words[randomIndex];
        return randomWord;
    }


}
