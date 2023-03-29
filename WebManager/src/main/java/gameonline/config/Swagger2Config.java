package gameonline.config;

import java.lang.annotation.Annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Configuration
@EnableSwagger2
public class Swagger2Config {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * Method to set paths to be included through swagger
//	 *
//	 * @return Docket
//	 */
//	@Bean
//	public Docket configApi() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).pathMapping("/").select()
//				.paths(regex("/api.*")).build();
//	}
//
//
//	/**
//	 * Method to set swagger info
//	 *
//	 * @return ApiInfoBuilder
//	 */
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("").description("").version("1.0").build();
//	}
	
	
	
	
//	Method[] methods = Controller01_LoginScreen.class.getMethods();
//	Annotation[][] annotations = methods[1].getParameterAnnotations();
//	for (Annotation[] annotation1 : annotations) {
//		for (Annotation annotation : annotation1) {
//			System.out.println("--->" + annotation.toString());
//			if (annotation instanceof RequestParam) {
//				RequestParam customAnnotation = (RequestParam) annotation;
//				System.out.println("name: " + customAnnotation.name());
//				System.out.println("value: " + customAnnotation.value());
//				System.out.println("defaultValue: " + customAnnotation.defaultValue());
//			}
//		}
//	}
	public static String getPath(Class<?> className) {
		Annotation annos[] = className.getAnnotations();
//		for (Annotation a : annos) {
//			if(a instanceof RequestMapping) {
//				RequestMapping rrr = (RequestMapping) a;
//				for(String ccc:rrr.path())
//					System.out.println("path : "+ccc);
//			}
//		}
		for (Annotation a : annos)
			if(a instanceof RequestMapping)
				return ((RequestMapping)a).path()[0].toString();
		return null;
	}
}