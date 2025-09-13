## 🎯 Git Convention

- feat: 새로운 기능 추가

- fix: 버그 수정

- docs: 문서 수정

- style: 코드 스타일 변경 (코드 포매팅, 세미콜론 누락 등)

- design: 사용자 UI 디자인 변경 (CSS 등)

- test: 테스트 코드, 리팩토링 (Test Code)

- refactor: 리팩토링 (Production Code)

- build: 빌드 파일 수정

- ci: CI 설정 파일 수정

- perf: 성능 개선

- chore: 자잘한 수정이나 빌드 업데이트

- rename: 파일 혹은 폴더명을 수정만 한 경우

- remove: 파일을 삭제만 한 경우

## 🪴 Branch Convention (GitHub Flow)

- `main`: 배포 가능한 브랜치, 항상 배포 가능한 상태를 유지
- `feature/{description}`: 새로운 기능을 개발하는 브랜치
    - 예: `feature/social-login`
- 브랜치 공유 X

**Flow**

1. `develop` 브랜치에서 새로운 브랜치를 생성. → `develop` 브랜치는 `main` 브랜치에서 생성
2. 작업을 완료하고 커밋 메시지에 맞게 커밋 후 푸시.
3. `develop` 으로 병합 시 Pull Request를 생성 / 팀원들의 리뷰.
4. 리뷰가 완료되면 `develop` 브랜치로 병합.
5. 병합 후, 배포 필요 시 `main` 브랜치로 Pull Request를 생성 / 팀원들의 리뷰 진행 
6. 병합 후 배포.
