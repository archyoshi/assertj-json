---
title: Contributing
sidebar_position: 8
---

# Contributing

We welcome contributions to AssertJ-JSON! Whether it is a bug report, feature request, or a pull request, your input is highly valued.

## Building the Project

If you want to contribute code, you can easily build the project locally using the provided Maven wrapper.

1. **Clone the repository:**
   ```bash
   git clone https://github.com/archyoshi/assertj-json.git
   cd assertj-json
   ```

2. **Build and test:**
   ```bash
   ./mvnw clean verify
   ```
   This will compile the code and run all the tests. Ensure that all tests pass before submitting a pull request.

## Pull Request Process

1. Fork the repository and create your branch from `main`.
2. Add tests for any new features or bug fixes.
3. Ensure the code builds and all tests pass via `./mvnw clean verify`.
4. Submit your pull request with a clear description of the changes.
