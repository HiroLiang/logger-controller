package hiroliang.tools.loggercontroller.util.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("hiroliang.tools.loggercontroller.util.annotations.AsDefaultMarkers")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AsDefaultMarkersProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Processing annotations " + annotations);
        for (Element element : roundEnv.getElementsAnnotatedWith(AsDefaultMarkers.class)) {
            String className = ((TypeElement) element).getQualifiedName().toString();
            System.out.println(className);
            try {
                JavaFileObject file = processingEnv.getFiler().createSourceFile("META-INF/services/com.example.DefaultMarkers");
                try (PrintWriter out = new PrintWriter(file.openWriter())) {
                    out.println(className);
                }
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
            }
        }
        return true;
    }
}
