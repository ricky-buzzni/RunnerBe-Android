/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserUseCaseModule.kt] created by Ji Sungbin on 22. 3. 20. 오전 12:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UserUseCaseModule.kt] created by Ji Sungbin on 22. 3. 20. 오전 12:39
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 22. 3. 2. 오후 9:30
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import team.applemango.runnerbe.domain.user.repository.UserRepository
import team.applemango.runnerbe.domain.user.usecase.UpdateBookmarkItemUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object UserUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideUpdateBookmarkItemUseCase(repo: UserRepository): UpdateBookmarkItemUseCase =
        UpdateBookmarkItemUseCase(repo)
}
