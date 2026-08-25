# Contributing

Thanks for helping improve AssertJ JSON.

## Development setup

- use JDK 25 or newer if possible
- run Maven with `mvn clean verify`
- keep code formatted with the project style
- write tests for every new assertion

## Coding conventions

- follow the AssertJ style for assertions and test naming
- use package-private test classes when possible
- prefer fluent assertion names such as `hasField`, `hasValueForField`, `isObject`
- add Javadoc to each public assertion method
- ensure test method names follow `should_pass_xxx` / `should_fail_xxx`

## Pull requests

- rebase your branch on the latest `main`
- keep PRs focused and reviewable
- do not merge `main` into your feature branch
- include tests and documentation where relevant

## License

By contributing, you agree that your work is submitted under the Apache License, Version 2.0.
