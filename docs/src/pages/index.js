import clsx from 'clsx';
import Link from '@docusaurus/Link';
import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';

import Heading from '@theme/Heading';
import styles from './index.module.css';

function HomepageHeader() {
  const {siteConfig} = useDocusaurusContext();
  return (
    <header className={clsx('hero hero--primary', styles.heroBanner)}>
      <div className="container">
        <Heading as="h1" className="hero__title">
          {siteConfig.title}
        </Heading>
        <p className="hero__subtitle">{siteConfig.tagline}</p>
        <div className={styles.buttons}>
          <Link
            className="button button--secondary button--lg"
            to="/docs/">
            Get Started
          </Link>
        </div>
      </div>
    </header>
  );
}

export default function Home() {
  const {siteConfig} = useDocusaurusContext();
  return (
    <Layout
      title={`Welcome to ${siteConfig.title}`}
      description="Fluent assertions for JSON in Java">
      <HomepageHeader />
      <main>
        <div className="container">
          <div className="row" style={{ marginTop: '2rem', marginBottom: '2rem', textAlign: 'center' }}>
            <div className="col col--12">
              <h2>Write cleaner, more readable JSON tests with AssertJ-JSON</h2>
              <p>
                A seamless extension to AssertJ for testing JSON strings without the hassle of manual parsing and complex assertions.
              </p>
            </div>
          </div>
        </div>
      </main>
    </Layout>
  );
}
