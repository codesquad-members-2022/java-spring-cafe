package kr.codesquad.cafe.system;

import com.samskivert.mustache.Mustache;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class DateTimeFormatAdvice {

    @ModelAttribute("formattedAsArticleTimestamp")
    public Mustache.Lambda formattedAsArticleTimestamp() {
        return (frag, out) -> out.append(LocalDateTime.parse(frag.execute().trim())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
