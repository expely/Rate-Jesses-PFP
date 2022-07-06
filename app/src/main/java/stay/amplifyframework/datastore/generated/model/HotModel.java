package stay.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the HotModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "HotModels", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class HotModel implements Model {
  public static final QueryField ID = field("HotModel", "id");
  public static final QueryField POINTS = field("HotModel", "Points");
  public static final QueryField URL = field("HotModel", "Url");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String Points;
  private final @ModelField(targetType="String", isRequired = true) String Url;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getPoints() {
      return Points;
  }
  
  public String getUrl() {
      return Url;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private HotModel(String id, String Points, String Url) {
    this.id = id;
    this.Points = Points;
    this.Url = Url;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      HotModel hotModel = (HotModel) obj;
      return ObjectsCompat.equals(getId(), hotModel.getId()) &&
              ObjectsCompat.equals(getPoints(), hotModel.getPoints()) &&
              ObjectsCompat.equals(getUrl(), hotModel.getUrl()) &&
              ObjectsCompat.equals(getCreatedAt(), hotModel.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), hotModel.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPoints())
      .append(getUrl())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("HotModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("Points=" + String.valueOf(getPoints()) + ", ")
      .append("Url=" + String.valueOf(getUrl()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static PointsStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static HotModel justId(String id) {
    return new HotModel(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      Points,
      Url);
  }
  public interface PointsStep {
    UrlStep points(String points);
  }
  

  public interface UrlStep {
    BuildStep url(String url);
  }
  

  public interface BuildStep {
    HotModel build();
    BuildStep id(String id);
  }
  

  public static class Builder implements PointsStep, UrlStep, BuildStep {
    private String id;
    private String Points;
    private String Url;
    @Override
     public HotModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new HotModel(
          id,
          Points,
          Url);
    }
    
    @Override
     public UrlStep points(String points) {
        Objects.requireNonNull(points);
        this.Points = points;
        return this;
    }
    
    @Override
     public BuildStep url(String url) {
        Objects.requireNonNull(url);
        this.Url = url;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String points, String url) {
      super.id(id);
      super.points(points)
        .url(url);
    }
    
    @Override
     public CopyOfBuilder points(String points) {
      return (CopyOfBuilder) super.points(points);
    }
    
    @Override
     public CopyOfBuilder url(String url) {
      return (CopyOfBuilder) super.url(url);
    }
  }
  
}
