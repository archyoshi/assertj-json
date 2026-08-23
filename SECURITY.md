# Security Policy

## Reporting a Vulnerability

If you discover a security vulnerability in AssertJ JSON, please **do not** create a public GitHub issue. Instead, please report it responsibly by:

1. **Email**: Send a detailed report to [security@example.com] with:
   - Description of the vulnerability
   - Steps to reproduce (if applicable)
   - Potential impact
   - Suggested fix (if you have one)

2. **Allow Time for Response**: Please give us reasonable time to respond and develop a fix before public disclosure (typically 30-90 days depending on complexity).

3. **No Disclosure Until Patch**: Please do not disclose the vulnerability publicly until we have released a patch and had time to notify users.

## Security Best Practices

As a JSON assertion library, AssertJ JSON:

- Does not execute arbitrary code from JSON content
- Validates JSON structure and data types safely
- Uses well-maintained dependencies (Jackson, AssertJ Core, JUnit)
- Follows OWASP security guidelines
- Performs regular dependency updates

## Supported Versions

We provide security updates for:

- Latest stable release: Full support
- Previous minor versions: Bug fixes for critical issues
- Major version N-1: Critical security fixes only

## Dependencies

AssertJ JSON depends on:

- [AssertJ Core](https://github.com/assertj/assertj) - Apache 2.0
- [Jackson](https://github.com/FasterXML/jackson-databind) - Apache 2.0
- [JUnit Jupiter](https://junit.org/junit5/) - EPL 2.0

We actively monitor these dependencies for security updates and release patches promptly.

## Acknowledgments

We appreciate the efforts of security researchers who responsibly disclose vulnerabilities to us. We will acknowledge your contribution in release notes if you wish.

---

Thank you for helping keep AssertJ JSON secure!
