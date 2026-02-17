package uz.lib.library.projections;

public interface BookProjection {
    Integer getId();
    String getName();
    String getDescription();
    Integer getCategoryId();
    Integer getAuthorId();
}
