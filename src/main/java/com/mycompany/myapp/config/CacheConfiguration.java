package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ordinateur.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ordinateur.class.getName() + ".stagiaires", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.VideoProjecteur.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.VideoProjecteur.class.getName() + ".modules", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Salle.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Salle.class.getName() + ".modules", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Stagiaire.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Gestionnaire.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Gestionnaire.class.getName() + ".cursuses", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Technicien.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Formateur.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Formateur.class.getName() + ".formateurMatieres", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Formateur.class.getName() + ".modules", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Formateur.class.getName() + ".indisponibilites", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Module.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Cursus.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Cursus.class.getName() + ".stagiaires", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Cursus.class.getName() + ".modules", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Matiere.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Matiere.class.getName() + ".formateurMatieres", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Matiere.class.getName() + ".modules", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.FormateurMatiere.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Indisponibilite.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
