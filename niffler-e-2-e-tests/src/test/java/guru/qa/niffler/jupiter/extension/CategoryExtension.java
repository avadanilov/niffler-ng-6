package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.UUID;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver {

  public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);

  private final SpendApiClient spendApiClient = new SpendApiClient();

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
        .ifPresent(anno -> {
          CategoryJson category = new CategoryJson(
                  null,
                  UUID.randomUUID().toString(),
                  anno.username(),
                  anno.archived()
          );
          CategoryJson created = spendApiClient.addCategory(category);
          context.getStore(NAMESPACE).put(
              context.getUniqueId(), created
          );
          if (anno.archived()) {
              CategoryJson archivedCategory = new CategoryJson(
                      created.id(),
                      created.name(),
                      created.username(),
                      true
              );
              created = spendApiClient.updateCategory(archivedCategory);
          }
        });
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
  }

  @Override
  public CategoryJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
  }
}
