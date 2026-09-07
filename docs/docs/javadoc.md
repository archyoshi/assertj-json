---
title: Javadoc
sidebar_position: 4
---

# Javadoc

You can browse the AssertJ-JSON Javadoc to explore all available assertions and utility methods.

## Online Javadoc

If you are pulling AssertJ-JSON from JitPack, JitPack automatically attempts to generate and host the Javadoc for your requested version. You can view the Javadoc for the latest builds directly on JitPack:

*   [AssertJ-JSON Javadoc on JitPack](https://jitpack.io/com/github/archyoshi/assertj-json/latest/javadoc/) *(Note: This link may take a moment to generate if the artifact was recently built).*

## Building Javadoc Locally

If you prefer to generate the Javadoc locally, you can clone the repository and run the Maven javadoc plugin:

```bash
git clone https://github.com/archyoshi/assertj-json.git
cd assertj-json
./mvnw javadoc:javadoc
```

The generated HTML documentation will be available in the `target/site/apidocs` directory. Open `target/site/apidocs/index.html` in your web browser.
