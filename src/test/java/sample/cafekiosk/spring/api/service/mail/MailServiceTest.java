package sample.cafekiosk.spring.api.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    // 일부는 실제 기능을 쓰고 싶고, 일부는 스터빙해서 가짜로 쓸려면 @Spy
    //@Spy
    @Mock
    private MailSendClient mailSendClient;

    // 전체다 가짜로 쓰려면 @Mock
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMail() {
        // given
//        MailSendClient mailSendClient = mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);

//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

//        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);

        //@Spy는 실제 객체를 사용하기 때문에 이렇게 스터빙 해줘야함
        doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());


        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }

    @Test
    @DisplayName("메일 전송 테스트 BDDMockito 사용해보기")
    void sendMailBDD() {

        //given

        //Mockito
//        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//               .thenReturn(true);

        //BDDMockito
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);


        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}