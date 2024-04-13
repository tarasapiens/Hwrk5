package VerifWokr2.EntityDB;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class PersUnInfo implements PersistenceUnitInfo {

    private final Properties proper;

    public PersUnInfo(Properties proper) {
        this.proper = proper;
    }

    @Override
    public Properties getProperties() {
        return proper;
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of(
                EmployeeEntity.class.getName()
        );
    }

    @Override
    public String getPersistenceUnitName() {
        return "TestPersUnInfo";
    }


    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }







    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return null;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }



    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }



    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
